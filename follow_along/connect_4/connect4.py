import numpy
import pygame
import sys
import math

# Make a 2d matrix as a board
def create_board(rows, cols):
    board = numpy.zeros((rows, cols))
    return board

# Put in (1) or (2) into the board at the specified position
def drop_piece(board, row, col, player):
    board[row][col] = player

# Check if spot is empty beforehand
def is_valid_location(board, col):
    return board[0][col] == 0

# Get the bottom most row number that is open
def get_next_open_row(board, col):
    for r in reversed(range(len(board))):
        if board[r][col] == 0:
            return r

# Check if the board is full (game is a tie)
def check_tie(board):
    for col in range(len(board[0])):
        if board[0][col] == 0:
            return False
    return True

# Check if a move connects 4 of the player's pieces
def winning_move(board, row, col, player):
    player += 1
    if winning_horizontal(board, row, col, player):
        return True
    elif winning_vertical(board, row, col, player):
        return True
    elif winning_diagonal(board, row, col, player):
        return True
    else:
        return False

# Check horizontal 4 in a row in relation to the new move
def winning_horizontal(board, row, col, player):
    counter = 0
    # 4 positions to consider
    for start_position in range(col-3, col+1):
        # Check if inside board
        if start_position in range(len(board[0])):
            # Start checking valid position and 4 in a row horizontally
            for curr_position in range(start_position, start_position+4):
                # Check if inside board
                if curr_position in range(len(board[0])):
                    if board[row][curr_position] == player:
                        counter += 1
                        if counter == 4:
                            return True
                    else:
                        break
                else:
                    break
        counter = 0
    return False

# Check vertial 4 in a row in relation to the new move
def winning_vertical(board, row, col, player):
    counter = 0
    # 4 positions to consider
    for start_position in range(row-3, row+1):
        # Check if inside board
        if start_position in range(len(board)):
            # Start checking valid position and 4 in a row vertically
            for curr_position in range(start_position, start_position+4):
                # Check if inside board
                if curr_position in range(len(board)):
                    if board[curr_position][col] == player:
                        counter += 1
                        if counter == 4:
                            return True
                    else:
                        break
                else:
                    break
        counter = 0
    return False

# Check diagonal 4 in a row in relation to the new move
def winning_diagonal(board, row, col, player):
    counter = 0
    # 8 positions to consider in total, 4 per major/minor diagonal
    # Check major diagonal
    for offset in range(0, 4):
        # Check if inside board
        row_start = row - offset
        col_start = col - offset
        if row_start in range(len(board)) and col_start in range(len(board[0])):
            for add_offset in range(0, 4):
                row_curr = row + add_offset
                col_curr = col + add_offset
                # Check if inside board
                if row_curr in range(len(board)) and col_curr in range(len(board[0])):
                    if board[row_curr][col_curr] == player:
                        counter += 1
                        if counter == 4:
                            return True
                    else:
                        break
                else:
                    break
        counter = 0

    counter = 0
    # Check minor diagonal
    for offset in range(0, 4):
        # Check if inside board
        row_start = row - offset
        col_start = col + offset
        if row_start in range(len(board)) and col_start in range(len(board[0])):
            for offset2 in range(0, 4):
                row_curr = row + offset2
                col_curr = col - offset2
                # Check if inside board
                if row_curr in range(len(board)) and col_curr in range(len(board[0])):
                    if board[row_curr][col_curr] == player:
                        counter += 1
                        if counter == 4:
                            return True
                    else:
                        break
                else:
                    break
        counter = 0
    return False

# Draw pygame board
def draw_board(board):
    # Fill in background with blue
    pygame.draw.rect(screen, BLUE, (0, SQUARESIZE, len(board[0]) * SQUARESIZE, len(board) * SQUARESIZE))

    # Fill in board with colored circles depending on values of 2d array board
    for row in range(len(board)):
        for col in range(len(board[0])):
            pos1 = int(col * SQUARESIZE + SQUARESIZE / 2)
            pos2 = int(row * SQUARESIZE + SQUARESIZE + SQUARESIZE / 2)
            # Empty
            if board[row][col] == 0:
                pygame.draw.circle(screen, BLACK, (pos1, pos2), RADIUS)
            # Player 1 = red
            elif board[row][col] == 1:
                pygame.draw.circle(screen, RED, (pos1, pos2), RADIUS)
            # Player 2 = yellow
            else:
                pygame.draw.circle(screen, YELLOW, (pos1, pos2), RADIUS)
    pygame.display.update()

# Draw circle following mouse movement
def draw_mouse(player):
    # Black out top for game ending message
    pygame.draw.rect(screen, BLACK, (0, 0, width, SQUARESIZE))
    position_x = event.pos[0]
    position_y = SQUARESIZE // 2

    if player == 0:
        pygame.draw.circle(screen, RED, (position_x, position_y), RADIUS)
    else:
        pygame.draw.circle(screen, YELLOW, (position_x, position_y), RADIUS)
    pygame.display.update()

# Draw player win message
def draw_winner(player):
    # Black out top for game ending message
    pygame.draw.rect(screen, BLACK, (0, 0, width, SQUARESIZE))

    msg = "Player {} wins!".format(player+1)
    if player == 0:
        color = RED
    else:
        color = YELLOW
    label = myfont.render(msg, 1, color)
    screen.blit(label, (40,10))
    pygame.display.update()
    pygame.time.wait(3000)

# Draw tie message
def draw_tie():
    # Black out top for game ending message
    pygame.draw.rect(screen, BLACK, (0, 0, width, SQUARESIZE))

    msg = "It's a tie!"
    label = myfont.render(msg, 1, BLUE)
    screen.blit(label, (120,10))
    pygame.display.update()
    pygame.time.wait(3000)

###############################################################################

board = create_board(6, 7)
game_over = False
same_turn = False
turn = 0

pygame.init()

SQUARESIZE = 100
RADIUS = 45
RED = (255, 0, 0)
YELLOW = (255, 255, 0)
BLUE = (0, 0, 255)
BLACK = (0, 0, 0)

width = len(board[0]) * SQUARESIZE
height = (len(board)+1)* SQUARESIZE
size = (width, height)
screen = pygame.display.set_mode(size)
myfont = pygame.font.SysFont("monospace", 75)

draw_board(board)

while not game_over:
    for event in pygame.event.get():
        # Exit game window
        if event.type == pygame.QUIT:
            sys.exit()

        # Follow mouse cursor
        if event.type == pygame.MOUSEMOTION:
            draw_mouse(turn)

        # Clicking to place a piece
        if event.type == pygame.MOUSEBUTTONDOWN:

            # Player 1 input
            if turn == 0:
                # Make clicked position a number from 0-6
                position_x = event.pos[0]
                col = math.floor(position_x / SQUARESIZE)

                # Check if piece can be placed there, then place it
                if is_valid_location(board, col):
                    # Update mouse color after a click
                    # So don't have to move before changing color
                    draw_mouse((turn + 1) % 2)
                    row = get_next_open_row(board, col)
                    drop_piece(board, row, col, 1)
                    same_turn = False
                else:
                    same_turn = True
            # Player 2 input
            else:
                # Make clicked position a number from 0-6
                position_x = event.pos[0]
                col = math.floor(position_x / SQUARESIZE)

                # Check if piece can be placed there, then place it
                if is_valid_location(board, col):
                    draw_mouse((turn + 1) % 2)
                    row = get_next_open_row(board, col)
                    drop_piece(board, row, col, 2)
                    same_turn = False
                else:
                    same_turn = True

            # Draw the board
            draw_board(board)

            if same_turn:
                continue
            else:
                # Check if game is over
                if winning_move(board, row, col, turn):
                    draw_winner(turn)
                    game_over = True
                elif check_tie(board):
                    draw_tie()
                    game_over = True
                turn = (turn + 1) % 2

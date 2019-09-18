// Cross off todo on click
$("ul").on("click", "li", function() {
    $(this).toggleClass("completed");
});

// Click X to delete todo
$("ul").on("click", "span", function(e) {
    $(this).parent().fadeOut(300, function() {
        $(this).remove();
    });
    e.stopPropagation();
});

// Add new todo
$("input[type='text']").keypress(function(e) {
    if (e.which === 13 && $(this).val().trim().length > 0) {
        let todo = $(this).val();
        $(this).val("");

        $("ul").append("<li><span><i class='far fa-trash-alt'></i></span> " + todo + "</li>");
    }
});

// Toggle adding new todo
$("#inputToggleIcon").click(function() {
    $("input[type='text']").fadeToggle(300);
    
    $("#line2").toggleClass("rotated");
});
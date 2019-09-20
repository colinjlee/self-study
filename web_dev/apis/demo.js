const request = require("request");

request("https://jsonplaceholder.typicode.com/todos", function(error, response, body) {
    if (!error && response.statusCode === 200) {
        // console.log(body);
        body = JSON.parse(body);
        console.log("Number of todos: " + body.length);
        
        let map = {};
        body.forEach(todo => {
            if (map[todo.userId]) {
                map[todo.userId]["total"] = map[todo.userId]["total"] + 1;
            } else {
                map[todo.userId] = {};
                map[todo.userId]["total"] = 1;
                map[todo.userId]["finished"] = 0;
                map[todo.userId]["unfinished"] = 0;
            }

            if (todo.completed) {
                map[todo.userId]["finished"] = map[todo.userId]["finished"] + 1;
            } else {
                map[todo.userId]["unfinished"] = map[todo.userId]["unfinished"] + 1;
            }
        });

        console.log(map);
    } else {
        console.log(error);
    }
});
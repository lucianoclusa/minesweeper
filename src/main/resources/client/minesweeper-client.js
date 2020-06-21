
var currentGame;

function createGame(userName, rows, cols, mines) {
	var settings = {
	  "url": "api-prod.eba-pziz29mt.us-east-2.elasticbeanstalk.com/minesweeper-api/",
	  "method": "POST",
	  "timeout": 0,
	  "headers": {
	    "Content-Type": "application/json"
	  },
	  "data": JSON.stringify({"user_name":userName,"number_of_rows":rows,"number_of_columns":cols,"number_of_mines":mines}),
	};

	$.ajax(settings).done(function (response) {
	  currentGame = response;
	  console.log(response);
	});
}

function makeMove(userName, gameId, row, col, move) {
	var settings = {
		"url": "api-prod.eba-pziz29mt.us-east-2.elasticbeanstalk.com/minesweeper-api/" + gameId,
		"method": "POST",
		"timeout": 0,
		"headers": {
			"Content-Type": "application/json"
		},
		"data": JSON.stringify({"user_id":userName,"row":row,"column":column,"movement_type":move}),
	};

	$.ajax(settings).done(function (response) {
		currentGame = response;
		console.log(JSON.stringify(response);
	});
}

function getGame(gameId) {
	var settings = {
		"url": "api-prod.eba-pziz29mt.us-east-2.elasticbeanstalk.com/minesweeper-api/" + gameId,
		"method": "GET",
		"timeout": 0,
		"headers": {
			"Content-Type": "application/json"
		}
	};

	$.ajax(settings).done(function (response) {
		currentGame = response;
		console.log(JSON.stringify(response);
	});
}

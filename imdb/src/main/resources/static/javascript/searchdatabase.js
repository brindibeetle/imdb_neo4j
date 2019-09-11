$(document).ready(function(){
	function showMovie(tconst, context) {
            $.get("/movie/findByTconst?tconst=" + encodeURIComponent(tconst), // todo fix paramter in SDN
                    function (data) {
                        if (!data) return;
                        var movie = data;
                        var primaryTitle = movie.primaryTitle;
                        $("#title-" + context).text(movie.primaryTitle);
                        var t = $("table#crew-" + context + " tbody").empty();
                        var movieRoles = movie.roles;
                        if (!movieRoles) return;
                        movieRoles.forEach(function (cast) {
                        	var nconst = cast.person.nconst;
                            $.get("/person/findByNconst?nconst=" + encodeURIComponent(nconst),
                            	function(personData) {
	                                var person = personData.primaryName;
	                                var category = cast.category;
	                                var characters = cast.characters;
	                                var job = cast.job;
	                                $("<tr><td class='movie'>" + category + "</td><td>" + person + "</td><td>" + job + "</td><td>" + characters + "</td></tr>").appendTo(t);
                            });
                        });
                    }, "json");
            return false;
        }
        function searchTitle() {
        	var context = "requirement1";
            var query=$("#searchTitle").find("input[name=searchTitle]").val();
            $.get("/movie/findByTitleContaining?title=" + encodeURIComponent(query),
                    function (data) {
                        var t = $("table#resultsMovies tbody").empty();
                        if (!data) return;
                        data.forEach(function (movie) {
                        	var averageRating = movie.averageRating;
                        	if (averageRating === null) averageRating = ""; else averageRating = averageRating.toString();
                            $('<tr><td class="hidden">' + movie.tconst + "</td><td class='movie'>" + movie.primaryTitle + "</td><td>" + movie.startYear + "</td><td>" + movie.genres + "</td><td>" + averageRating + "</td></tr>").appendTo(t)
                                    .click(function() { showMovie(movie.tconst, context);})
                        });
                        showMovie(data[0].tconst, context);
                    }, "json");
            return false;
        }
        
        $("#searchTitle").submit(searchTitle);
//        searchTitle();

        function searchGenre() {
        	var context = "requirement2";
            var query=$("#searchGenre").find("input[name=searchGenre]").val();
            $.get("/movie/findByGenreContaining?genre=" + encodeURIComponent(query),
                    function (data) {
                        var t = $("table#resultsGenre tbody").empty();
                        if (!data) return;
                        data.forEach(function (movie) {
                        	var averageRating = movie.averageRating;
                        	if (averageRating === null) averageRating = ""; else averageRating = parseFloat(averageRating);
                            $('<tr><td class="hidden">' + movie.tconst + "</td><td class='movie'>" + movie.primaryTitle + "</td><td>" + movie.startYear + "</td><td>" + movie.genres + "</td><td>" + averageRating + "</td></tr>").appendTo(t)
                                    .click(function() { showMovie(movie.tconst, context);})
                        });
                        showMovie(data[0].tconst, context);
                    }, "json");
            return false;
        }
        
        $("#searchGenre").submit(searchGenre);
//        searchGenre();

    	function showPerson(number, nconst) {
            $.get("/person/findByNconst?nconst=" + encodeURIComponent(nconst),
                function (data) {
	                var t = $("table#resultsPerson" + number + "_requirement3 tbody").empty();
	                if (!data) return;
	                var person = data;
                    $("<tr><td class='nconst hidden'>" + person.nconst + "</td><td class='movie'>" + person.primaryName + "</td><td>" + person.birthYear + "</td><td>" + person.deathYear + "</td><td>" + "</td></tr>").appendTo(t);
	            }, "json");
            $("#heading_requirement3_" + number).text("Choosen");
            $("#searchPerson" + number).find("input[name=nconst_requirement3_" + number + "]").val(nconst);
            return false;
        }
        function searchPerson(number) {
            $("#heading_requirement3_" + number).text("Searching...");
            var query=$("#searchPerson" + number).find("input[name=searchPerson" + number + "]").val();
            $.get("/person/findByNameContaining?name=" + encodeURIComponent(query),
                function (data) {
                    var t = $("table#resultsPerson" + number + "_requirement3 tbody").empty();
                    $("#heading_requirement3_" + number).text("Search results");
                    if (!data) return;
                    data.forEach(function (person) {
                        $("<tr><td class='nconst hidden'>" + person.nconst + "</td><td class='movie'>" + person.primaryName + "</td><td>" + person.birthYear + "</td><td>" + person.deathYear + "</td><td>" + "</td></tr>").appendTo(t)
                                .click(function() { showPerson(number, $(this).find("td.nconst").text());})
                    });
                }, "json");
            $("#searchPerson" + number).find("input[name=nconst_requirement3_" + number + "]").val("");
            $("#requirement3_result_form").find("input[name=requirement3_result]").val("")
            return false;
        }
        function searchPerson1() {
        	return searchPerson(1);
        }
        function searchPerson2() {
        	return searchPerson(2);
        }
        
        $("#searchPerson1").submit(searchPerson1);
        $("#searchPerson2").submit(searchPerson2);
        searchPerson1();
        searchPerson2();
        
        function degreeOfSeparation(){
            var nconst1=$("#searchPerson1").find("input[name=nconst_requirement3_1]").val();
            var nconst2=$("#searchPerson2").find("input[name=nconst_requirement3_2]").val();

            $.get("/person/findTheDegreeOfSeparation?nconst1=" + encodeURIComponent(nconst1) + "&nconst2=" + encodeURIComponent(nconst2),
            	function (data) {
            		if (!data) return;
            		result = data;;
	            	$("#requirement3_result_form").find("input[name=requirement3_result]").val(result);
    	        }, "json");
            return false;
        }

        $("#requirement3_result_form").submit(degreeOfSeparation);

    })

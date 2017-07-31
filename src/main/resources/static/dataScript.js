$(document).ready(

    function () {
        var xvals = [0,1,2,3];
        var yvals = [1,2,3,4];
        refreshContents();

        $("#refreshButton").click(function () {
            refreshContents();
        });

        $('#inputhashtag').keypress(function(e){
            if(e.keyCode==13){
                $('#refreshButton').click();
            }
        });

        function refreshContents(){
            var hashtagInput = $("#inputhashtag").val();
            getHashTag(hashtagInput);
            updatePlot();
            checkTime();
        };

        function getHashTag(hashTag){
            var period;
            var radios = document.getElementsByName("period");
            for (var i = 0, length = radios.length; i < length; i++) {
                if (radios[i].checked) {
                    period = radios[i].value;
                }
            }
            $.get("getHashtag", {hashTag:hashTag, period:period}, function(data){
                var jsonObj = JSON.parse(data);
                xvals = jsonObj.xvals;
                yvals = jsonObj.yvals;
                $("#displaying").html("Hashtag: " + hashTag);
            })
        };

        function updatePlot(){
            var hPlot = document.getElementById("hashtagPlot");
            Plotly.purge("hashtagPlot")
            Plotly.plot( hPlot, [{
                x: xvals,
                y: yvals }], {
                margin: { t: 0 }});
        };

        function checkTime(){
            var radios = document.getElementsByName("period");
            var timespan = document.getElementById("timespan");
            for (var i = 0, length = radios.length; i < length; i++) {
                if (radios[i].checked) {
                    $("#timespan").html("Time: " + radios[i].value);
                }
            }
        };

    }

);
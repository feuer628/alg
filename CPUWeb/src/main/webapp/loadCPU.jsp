<%--suppress ALL --%>
<!DOCTYPE html>
<html>
<head>
    <script type='text/javascript' src='js/jquery-1.11.2.min.js'></script>
</head>

    <body>
        <h2>
            Load CPU Percent
        </h2>

        <div>
            <img id = "content" alt="no img" src="<%=request.getAttribute("loadCPUImj")%>"/>
            <br>

        </div>
        <div id = "answer">
        </div>
        <h4></h4>
    </body>
</html>

<script>

        setInterval(function () {
            $.ajax({
                url: 'loadCPU',
                data: {
                    action: "refresh"
                },
                success: function (response) {
                    $("#content").attr("src", response);
                }
            });

            $.ajax({
                url: 'loadCPU',
                data: {
                    action: "answerWS"
                },
                success: function (response) {
                    $('<p>'+response+'</p>').appendTo('#answer');
                }
            });
        }, 500);

</script>
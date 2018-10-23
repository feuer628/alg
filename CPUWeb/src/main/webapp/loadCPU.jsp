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
        var qName= Math.floor(Math.random()*10000);
        setInterval(function () {
            $.ajax({
                url: 'loadCPU',
                data: {
                    action: "refresh",
                    qname: qName
                },
                success: function (response) {
                    $("#content").attr("src", response);
                }
            });

            $.ajax({
                url: 'loadCPU',
                data: {
                    action: "answerWS",
                    qname: qName
                },
                success: function (response) {
                    $('<p>'+response+'</p>').appendTo('#answer');
                }
            });
        }, 1000);

</script>
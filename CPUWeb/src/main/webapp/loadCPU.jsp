<!DOCTYPE html>


<%@page import="com.wd45.rabbitmq.RabbitMQConsumer"%>
<html>
<head>
    <script type='text/javascript' src='css/jquery-1.11.2.min.js'></script>
</head>

    <body>
        <h2 id="idd">
            Load CPU Percent
        </h2>

        <div id = "content">
            <img alt="no img" src="<%=request.getAttribute("loadCPUImj")%>"/>
        </div>

        <h4><%=RabbitMQConsumer.getMessages()%></h4>
    </body>
</html>

<script>
    setInterval(function() {
            $.ajax({
                url : 'loadCPU',     // URL - сервлет
                data : {                 // передаваемые сервлету данные
                },
                success : function(response) {
                    // обработка ответа от сервера
                    //$('#idd').text(response);
                }
            });
    }, 1000);

</script>
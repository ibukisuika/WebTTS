<html>
<body>
	<h1>Please input the text</h1>
    <textarea id="textarea" cols="20" rows="25"></textarea>
    <div>
        <button onclick="speak()">Speak</button>
    </div>
    <audio src="http://localhost:8080/speak?word=morning" id="audio">
        Your browser does not support this audio format.
    </audio>
</body>
</html>

<script>
    function speak(){
        audio = document.getElementById("audio");
        audio.setAttribute("src", "/speak?word='" + document.getElementById("textarea").value + "'");
        audio.play();
    }
</script>
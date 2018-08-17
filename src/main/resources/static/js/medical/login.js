$(document).ready(function () {

    var meta = $("#error").attr("content");

    if(meta =="true"){
        $("#email").css("border", "1px solid red");
        $("#email").css("color", "red");
        $("#name").css("border", "1px solid red");
        $("#name").css("color", "red");
    }

    $(".form-control").on("input", function () {
        var id = $(this).attr("id");
        if (id == "name") {
            var username = $(this).val();
            if (username == null || username.length < 2 || username.length > 255) {
                $("#name").css("border", "1px solid red");
                $("#name").css("color", "red");
            } else if (username.length < 4) {
                $("#name").css("border", "1px solid rgb(114, 129, 33);");
                $("#name").css("color", "rgb(114, 129, 33);");
            } else {
                $("#name").css("border", "1px solid #07a7e3");
                $("#name").css("color", "#07a7e3");
            }
        } else {
            var password = $(this).val();
            if (password == null || password.length < 2 || password.length > 255) {
                $("#email").css("border", "1px solid red");
                $("#email").css("color", "red");
            } else if (password.length < 6) {
                $("#email").css("border", "1px solid rgb(114, 129, 33);");
                $("#email").css("color", "rgb(114, 129, 33);");
            } else {
                $("#email").css("border", "1px solid #07a7e3");
                $("#email").css("color", "#07a7e3");
            }
        }
    })

    $("#contact").on("submit", function (event) {
        var password = $("#email").val();
        var username = $("#name").val();
        if (username == null || username.length < 2 || username.length > 255) {
            event.preventDefault();
            $("#name").css("border", "1px solid red");
            $("#name").css("color", "red");
        } else if (username.length < 4) {
            event.preventDefault();
            $("#name").css("border", "1px solid rgb(114, 129, 33);");
            $("#name").css("color", "rgb(114, 129, 33);");
        } else if (password == null || password.length < 2 || password.length > 255) {
            event.preventDefault();
            $("#email").css("border", "1px solid red");
            $("#email").css("color", "red");
        } else if (password.length < 6) {
            event.preventDefault();
            $("#email").css("border", "1px solid rgb(114, 129, 33);");
            $("#email").css("color", "rgb(114, 129, 33);");
        }
    })


});
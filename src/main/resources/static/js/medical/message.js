$(document).ready(function () {

    $(".form-control").on("input", function () {
        var value = $(this).val();
        var name = $(this).attr("name");
        isValidData(name, value);
    });

    $("#contactMe").on("submit", function (event) {
        event.preventDefault();
        var inputTags = $(this).children(".form-control");
        $.each(inputTags, function (i, input) {
            var value = $(input).val();
            var name = $(input).attr("name");
            isValidData(name, value)
        });
        if (flag) {
            var title = $("#title").val();
            var date = $("#date").val();
            var message = $("#message").val();
            var doctorId = $("#doctorId").val();
            $.ajax({
                type: "POST",
                url: "/user/request/add",
                data: {_csrf: token,title: title,date: date,message: message,doctorId: doctorId},
                success: function (data) {
                    if(data.success){
                        alert(successMsg);
                        $(".form-control").val("")
                        var array = $("#new_request").text().split("(");
                        var value = array[1].split(")")[0];
                        $("#new_request").text(array[0] + "(" + (parseInt(value) + 1) +")")
                    }else if(data.titleError){
                        $("#titleError").text(titleError);
                    }else if(data.messageerror){
                        $("#messageError").text(messageError);
                    }else if(data.dateError){
                        $("#dateError").text(dateError);
                    }else if(data.exists){
                        alert(existsMsg);
                        $(".form-control").val("");
                    }else {
                        alert("else ?????")
                    }
                },
                error: function () {
                    window.location = "/error";
                }
            })
        }
    })
});
var titleError = $("#title_error").attr("content");
var dateError = $("#date_error").attr("content");
var messageError = $("#message_error").attr("content");
var successMsg = $("#success").attr("content");
var existsMsg = $("#exists").attr("content");
var token = $("#frsc").attr("content");
var flag = false;

function isValidData(name, value) {
    switch (name) {
        case "title":
            if (value == null || value.length < 4 || value.length > 255) {
                $("#titleError").text(titleError);
                flag = false;
            } else {
                $("#titleError").text("");
                flag = true;
            }
            ;
            break;
        case "message":
            if (value == null || value.length < 10) {
                $("#messageError").text(messageError);
                flag = false;
            } else {
                $("#messageError").text("");
                flag = true;
            }
            break;
        case "date":
            if (value == null || value.length != 16) {
                $("#dateError").text(dateError);
                flag = false;
            } else {
                $("#dateError").text("");
                flag = true;
            }
    }
}
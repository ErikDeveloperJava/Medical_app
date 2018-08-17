$(document).ready(function () {

    $(".form-control").on("input",function () {
        var value = $(this).val();
        var name = $(this).attr("name");
        var index = $(this).attr("title");
        isValidData(name,value,index,null);
    });

    $(".doctor-form").on("submit",function (event) {
        var inputTags = $(this).children(".form-control");
        $.each(inputTags,function (i, input) {
            var value = $(input).val();
            var name = $(input).attr("name");
            var index = $(input).attr("title");
            isValidData(name,value,index,event)
        })
    });

    $
});
var error = $("#defaultError").attr("content");
var nameError = $("#name_error").attr("content");
var surnameError = $("#surname_error").attr("content");
var usernameError = $("#username_error").attr("content");
var oldPasswordError = $("#old_password_error").attr("content");
var newPasswordError = $("#new_password_error").attr("content");

function isValidData(name, value, index,event) {
    switch (name) {
        case "workingDays":
            if(value == null || value.length < 4 || value.length > 255){
                if(event != null){
                    event.preventDefault();
                }
                $("#" + index).text(error);
            }else {
                $("#" + index).text("");
            };
            break;
        case "name":
            if(value == null || value.length < 4 || value.length > 255){
                if(event != null){
                    event.preventDefault();
                }
                $("#nameError").text(nameError);
            }else {
                $("#nameError").text("");
            };
            break;
        case "surname":
            if(value == null || value.length < 4 || value.length > 255){
                if(event != null){
                    event.preventDefault();
                }
                $("#surnameError").text(surnameError);
            }else {
                $("#surnameError").text("");
            };
            break;
        case "username":
            if(value == null || value.length < 4 || value.length > 255){
                if(event != null){
                    event.preventDefault();
                }
                $("#usernameError").text(usernameError);
            }else {
                $("#usernameError").text("");
            };
            break;
        case "oldPassword":
            if(value == null || value.length < 6 || value.length > 255){
                if(event != null){
                    event.preventDefault();
                }
                $("#oldPasswordError").text(oldPasswordError);
            }else {
                $("#oldPasswordError").text("");
            };
            break;
        case "newPassword":
            if(value == null || value.length < 6 || value.length > 255){
                if(event != null){
                    event.preventDefault();
                }
                $("#newPasswordError").text(newPasswordError);
            }else {
                $("#newPasswordError").text("");
            };
    }
};
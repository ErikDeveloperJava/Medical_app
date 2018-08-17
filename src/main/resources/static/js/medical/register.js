$(document).ready(function () {

    var nameError = $("#nameError").attr("content");
    var surnameError = $("#surnameError").attr("content");
    var usernameError = $("#usernameError").attr("content");
    var passwordError = $("#passwordError").attr("content");
    var repeatPasswordError = $("#repeatPasswordError").attr("content");
    var imageError = $("#imageError").attr("content");
    var passwordsDoNotMatches = $("#passwordsNotMatch").attr("content");
    var informationError = $("#informationError").attr("content");

    $(document).on("input", ".form-control", function () {
        var value = $(this).val();
        switch ($(this).attr("name")) {
            case "name":
                if (value == null || value.length < 2 || value.length > 255) {
                    $("#nameError_").text(nameError);
                } else {
                    $("#nameError_").text("");
                }
                ;
                break;
            case "surname":
                if (value == null || value.length < 4 || value.length > 255) {
                    $("#surnameError_").text(surnameError);
                } else {
                    $("#surnameError_").text("");
                }
                ;
                break;
            case "username":
                if (value == null || value.length < 4 || value.length > 255) {
                    $("#usernameError_").text(usernameError);
                } else {
                    $("#usernameError_").text("");
                }
                ;
                break;
            case "password":
                if (value == null || value.length < 6 || value.length > 255) {
                    $("#passwordError_").text(passwordError);
                } else if ($("#repeat_password").val() != value) {
                    $("#passwordError_").text(passwordsDoNotMatches);
                    $("#repeatPasswordError_").text(passwordsDoNotMatches);
                } else if ($("#repeat_password").val() == value) {
                    $("#passwordError_").text("");
                    $("#repeatPasswordError_").text("");
                } else {
                    $("#passwordError_").text("");
                }
                ;


                break;

            case "repeatPassword":
                if (value == null || value.length < 6 || value.length > 255) {
                    $("#repeatPasswordError_").text(repeatPasswordError);
                } else if ($("#password").val() != value) {
                    $("#repeatPasswordError_").text(passwordsDoNotMatches);
                    $("#passwordError_").text(passwordsDoNotMatches);
                } else if ($("#password").val() == value) {
                    $("#passwordError_").text("");
                    $("#repeatPasswordError_").text("");
                } else {
                    $("#repeatPasswordError_").text("");
                }
                ;
                break;
            case "information":
                if (value == null || value.length < 10) {
                    $("#informationError_").text(informationError);
                } else {
                    $("#informationError_").text("")
                }
                ;


        }
    });

    $(".inputfile").on("input", function () {
        var value = $(this).val();
        if (value == null || value.length == 0 || value == "") {
            $("#imageError_").text(imageError)
        } else {
            $("#imageError_").text("")
        }
    })

    $("#contact").on("submit", function (event) {
        var childrens = $(this).children(".form-control");
        $.each(childrens, function (i, input) {

            var value = $(input).val();
            switch ($(input).attr("name")) {
                case "name":
                    if (value == null || value.length < 2 || value.length > 255) {
                        event.preventDefault();
                        $("#nameError_").text(nameError);
                    } else {
                        $("#nameError_").text("");
                    }
                    ;
                    break;
                case "surname":
                    if (value == null || value.length < 4 || value.length > 255) {
                        event.preventDefault();
                        $("#surnameError_").text(surnameError);
                    } else {
                        $("#surnameError_").text("");
                    }
                    ;
                    break;
                case "username":
                    if (value == null || value.length < 4 || value.length > 255) {
                        event.preventDefault();
                        $("#usernameError_").text(usernameError);
                    } else {
                        $("#usernameError_").text("");
                    }
                    ;
                    break;
                case "password":
                    if (value == null || value.length < 6 || value.length > 255) {
                        event.preventDefault();
                        $("#passwordError_").text(passwordError);
                    } else if ($("#repeat_password").val() != value) {
                        event.preventDefault();
                        $("#passwordError_").text(passwordsDoNotMatches);
                        $("#repeatPasswordError_").text(passwordsDoNotMatches);
                    } else if (value != null && value.length != 0 && $("#repeat_password").val() == value) {
                        $("#passwordError_").text("");
                        $("#repeatPasswordError_").text("");
                    } else {
                        $("#passwordError_").text("");
                    }
                    ;
                    break;

                case "repeatPassword":
                    if (value == null || value.length < 6 || value.length > 255) {
                        event.preventDefault();
                        $("#repeatPasswordError_").text(repeatPasswordError);
                    } else if ($("#password").val() != value) {
                        event.preventDefault();
                        $("#repeatPasswordError_").text(passwordsDoNotMatches);
                        $("#passwordError_").text(passwordsDoNotMatches);
                    } else if (value != null && value.length != 0 && $("#password").val() == value) {
                        $("#passwordError_").text("");
                        $("#repeatPasswordError_").text("");
                    } else {
                        $("#repeatPasswordError_").text("");
                    }
                    ;
                    break;
                case "information":
                    if (value == null || value.length < 10) {
                        $("#informationError_").text(informationError);
                    } else {
                        $("#informationError_").text("")
                    }
                    ;
            }
            var image = $("#file").val();

            if (image == null || image.length == 0) {
                event.preventDefault();
                $("#imageError_").text(imageError)
            } else {
                $("#imageError_").text("")
            }
        })
    });
});
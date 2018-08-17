$(document).ready(function () {
    var token = $("#frsc").attr("content");

    $(".cancel").on("click",function (event) {
        event.preventDefault();
        var array = $("#new_request").text().split("(");
        var text = array[0];
        var value = array[1].split(")")[0];
        var id = $(this).parent().children(".registeredId").val();
        $(this).parent().parent().remove();
        $.ajax({
            type: "POST",
            url: "/user/request/delete",
            data: {_csrf: token,registeredId : id},
            success: function () {
                $(this).parent().parent().remove();
                $("#new_request").text(text + "(" + (parseInt(value) - 1) + ")")
            },
            error: function () {
                window.location = "/error";
            }
        })
    });

    $(".confirm").on("click",function (event) {
        event.preventDefault();
        var parent = $(this).parent().parent();
        var id = $(this).parent().children(".registeredId").val();
        var array = $("#accepted_registered").text().split("(");
        var acceptedRegisteredText = array[0];
        var acceptedRegisteredValue = array[1].split(")")[0];
        array = $("#unconfirmed_registered").text().split("(");
        var unconfirmedRegisteredtext = array[0];
        var unconfirmedRegisteredvalue = array[1].split(")")[0];
        $.ajax({
            type: "POST",
            url: "/doctor/users/accepted",
            data: {_csrf: token,registeredId: id},
            success: function () {
                parent.remove();
                $("#unconfirmed_registered").text(unconfirmedRegisteredtext + "(" + (parseInt(unconfirmedRegisteredvalue) - 1) + ")")
                $("#accepted_registered").text(acceptedRegisteredText + "(" + (parseInt(acceptedRegisteredValue) + 1) + ")")

            },
            error: function () {
                window.location = "/error"
            }
        })
    });

    $(".delete").on("click",function (event) {
        event.preventDefault();
        var status = $("#status").attr("content");
        var id = $(this).parent().children(".registeredInfoId").val();
        var parent = $(this).parent().parent();
        var array = $("#new_registered").text().split("(");
        var newRegisteredtext = array[0];
        var newRegisteredvalue = array[1].split(")")[0];
        array = $("#unconfirmed_registered").text().split("(");
        var unconfirmedRegisteredtext = array[0];
        var unconfirmedRegisteredvalue = array[1].split(")")[0];
        array = $("#accepted_registered").text().split("(");
        var acceptedRegisteredtext = array[0];
        var acceptedRegisteredvalue = array[1].split(")")[0];
        $.ajax({
            type: "POST",
            url: "/doctor/users/delete",
            data: {_csrf: token,registeredId: id},
            success: function () {
                parent.remove();
                if(status == "unconfirmed" || status == "new"){
                    $("#unconfirmed_registered").text(unconfirmedRegisteredtext + "(" + (parseInt(unconfirmedRegisteredvalue) - 1) + ")")
                }else {
                    $("#accepted_registered").text(acceptedRegisteredtext + "(" + (parseInt(acceptedRegisteredvalue) - 1) + ")")
                }
            },
            error: function () {
                window.location = "/error"
            }
        })
    });
});
$(document).ready(function () {

    $(".form-control").on("input", function () {
        var value = $(this).val();
        var name = $(this).attr("name");
        isValidData(name,value,null);
    })

    $("#file").on("change",function () {
        var value = $(this).val();
        var name = $(this).attr("name");
        isValidData(name,value,null);
    })

    $("#contact").on("submit",function (event) {
        var inputTags = $(this).children(".form-control");
        $.each(inputTags,function (i, input) {
            var value = $(input).val();
            var name = $(input).attr("name");
            isValidData(name,value,event)
        })
        var imgInput = $("#file");
        var value = imgInput.val();
        var name = imgInput.attr("name");
        isValidData(name,value,event);
    })
});

var titleError = $("#titleError").attr("content");
var descriptionError = $("#descriptionError").attr("content");
var imageError = $("#imageError").attr("content");


function isValidData(name, value,event) {
    switch (name) {
        case "title":
            if (value == null || value.length < 4 || value.length > 255) {
                if(event != null){
                    event.preventDefault();
                }
                $("#titleError_").text(titleError)
            } else {
                $("#titleError_").text("")
            }
            break;

        case "description":
            if (value == null || value.length < 10) {
                if(event != null){
                    event.preventDefault();
                }
                $("#descriptionError_").text(descriptionError);
            } else {
                $("#descriptionError_").text("");
            }
            break;

        case "image":
            if (value == null || value.length == 0) {
                if(event != null){
                    event.preventDefault();
                }
                $("#imageError_").text(imageError);
            } else {
                $("#imageError_").text("");
            }
            break;
    }
}
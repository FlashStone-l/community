$(function(){
    $("#publish").click(function(){
        const title= $("#title").val();
        const description = $("#description").val();
        const tag= $("#tag").val();
        if(title === ""){
            alert("标题不能为空");
            return false;
        }
        if(description === ""){
            alert("描述不能为空");
            return false;
        }
        if(tag=== ""){
            alert("标签不能为空");
            return false;
        }

        $("#publish").submit();
    });
});
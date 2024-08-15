$.ajaxSetup({
    complete: function (XMLHttpRequest, textStatus) {
        var tokenFromHeader;

        // 尝试从响应头中获取token
        if (XMLHttpRequest.readyState === 4 && // 确保请求已完成
            XMLHttpRequest.status >= 200 && XMLHttpRequest.status < 300) { // 确保请求成功
            tokenFromHeader = XMLHttpRequest.getResponseHeader('token'); // 假设token在响应头中名为'token'

            if (tokenFromHeader) {
                // 如果找到了token，则更新localStorage
                console.log("更新了token");
                localStorage.setItem('token', tokenFromHeader);
            }
        }

        // 处理不同的HTTP状态码
        if (XMLHttpRequest.status === 403) {
            layer.msg('权限不足，请联系管理员', {icon: 2});
        } else if (XMLHttpRequest.status === 500) {
            layer.msg('服务器内部错误，请稍后再试', {icon: 2});
        } else if (XMLHttpRequest.status !== 200 && XMLHttpRequest.status !== 201 && XMLHttpRequest.status !== 204) { // 也可以包括其他成功的状态码，如201（Created）或204（No Content）
            layer.msg('请求失败，请稍后再试', {icon: 2});
        }
    }
});
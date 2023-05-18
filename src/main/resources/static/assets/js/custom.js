function makeApiRequest(requestMethod, apiDetails, requestData, callback) {

    var messageId = "#" + apiDetails.apiMessageDisplayId;
    var apiResponse = null;
    var apiResponseData = null;
    var errorInAPIResponse = null;
    // var url = "http://localhost:8081/api/";
    // var url = "http://192.168.29.23:8080/sanimaotp/api/";
    var url = BASE_URL;
    // var url = "http://192.168.1.4:8080/sanimaotp/api/";

    var localAuth= {
        username: 'apiuser',
        password: 'test@123'
    };


    var headers = {'Content-Type': 'application/json'}
    if(apiDetails.controllerName=="abinTest"){
        headers = {'Content-Type': 'multipart/form-data'}
    }


    $(messageId).html(apiDetails.loadingMessage);
    $(messageId).attr("style", "color:purple;margin-left:15px");


    setTimeout(function(){
        axios.request({
            auth:localAuth,
            method: requestMethod,
            url: url + apiDetails.controllerName,
            data: requestData,
            headers: headers
        }).then(function (response) {
            console.log(JSON.stringify(response.data))

            if (response.data) {
                apiResponse = response.data;
            }
        }).catch(function (error) {
            var networkErrorMessage = null;

            if (error.response) {
                console.log(JSON.stringify(error.response))
                var mess = error.response.data.message;
                networkErrorMessage = {data: null, status: 0, message: "Server could not generate response data"}
            }
            else if (error.request) {
                console.log(error.request)
                networkErrorMessage = {
                    message: "Server could not be reached, Check internet connection!!!",
                    status: 0,
                    data: null
                }
            }
            else {
                console.log(error)
                networkErrorMessage = {
                    message: "Error has occured!!",
                    status: 0,
                    data: null
                }
            }
            errorInAPIResponse = networkErrorMessage;

        }).then(function () {
            if (apiResponse) {
                if (apiResponse.status == 1) {
                    apiResponseData = {
                        data: apiResponse.data,
                        message: apiResponse.message,
                        status: apiResponse.status,
                    };
                } else {
                    errorInAPIResponse = {
                        data: apiResponse.data,
                        message: apiResponse.message,
                        status: apiResponse.status
                    };

                }
            }
            console.log("API response ", apiResponseData, errorInAPIResponse)
            var responseData = {
                data: apiResponseData,
                error: errorInAPIResponse,
                requestDetails: {apiDetails: apiDetails, requestData: requestData}
            }
            setTimeout(function () {
                if (responseData.data) {
                    $(messageId).html(responseData.data.message);
                    if(apiDetails.hideMessageField){
                        responseData.data.status == 1 ? $(messageId).attr("style", "color:transparent;border:1px solid transparent;padding:4px") : $(messageId).attr("style", "color:red;");
                    }else{
                        responseData.data.status == 1 ? $(messageId).attr("style", "color:green;border:1px solid green;padding:4px;") : $(messageId).attr("style", "color:red;");
                    }

                } else {
                    $(messageId).html(responseData.error.message);
                    $(messageId).attr("style", "color:red;");
                }

                callback(responseData)
            },100)
        })
    },1000)


}
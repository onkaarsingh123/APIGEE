/* This resource creates the log data to be used in MessageLogging Policy for further logging in a 3rd party
log management system like Loggly.
App and Request details are logged */

print('URI REQUEST.RESOURCE NAME' + context.getVariable('primaryResource'));
//This function returns json object containing all response headers with their values.
function getHeaders( headers){
    result = {};
    headers = headers + '';
    headers = headers.slice(1, -1).split(', ');
    headers.forEach(function(x){
      var a = context.getVariable( "message.header." + x );
      result[x] = a;
    });
    return result;
}

try{
    // API Details
    var primaryResource = context.getVariable("primaryResource");
    if(!primaryResource){
          primaryResource = "Base Resource"  
    }
    var clientScheme = context.getVariable("client.scheme");
    var organizationName = context.getVariable("organization.name");
    var environmentName = context.getVariable("environment.name");
    var apiproxyName = context.getVariable("apiproxy.name");
    var proxyRequest = context.getVariable("proxyRequest");
    
    //Request Details:
    var proxyRequestUrl = clientScheme + "://" + organizationName + "-" + environmentName + ".apigee.net" + proxyRequest;
    var requestContentHeader = context.getVariable('requestContentHeader');
    var requestAcceptHeader = context.getVariable("request.header.Accept");
    var requestPayload = context.getVariable('requestPayload');
    var proxyVerb = context.getVariable("proxyVerb");
    
    //ResponseDetails:
    var responseCode = context.getVariable("message.status.code");
    var responseBody =  context.getVariable("message.content");
    var contentType = context.getVariable("message.header.Content-Type");
    var responseHeaders = context.getVariable("message.headers.names");
    var allResponseHeaders = getHeaders(responseHeaders);
    
    //FlowDetails:
    var flowName = context.getVariable('current.flow.name');
    
    var applicationName = context.getVariable("developer.app.name");
    var userId = context.getVariable('userId');
    
    var logsObj = {};
    
         //application json data
        var application = {};
        if(applicationName){
            application["Name"] = applicationName;    
        }
        if(userId){
            application["UserName"] = userId;
        }
        
        logsObj.Application = application ; 
        
        //api json data
        var api = {};
        if(apiproxyName){
            api["ApiProxy"] =  apiproxyName;
        }
        if(organizationName){
            api["Organization"] =  organizationName;
        }
        if(environmentName){
            api["Environment"] =  environmentName;
        }
        if(primaryResource){
            api["FhirResource"] =  primaryResource;
        }
        logsObj.API = api;
        
        //request json data
        var request = {};
        if(proxyRequestUrl){
            request["Url"] =  proxyRequestUrl;
        }
        if(proxyVerb){
            request["Method"] =  proxyVerb;
        }
        
        var reqHeaders = {};
        if(requestContentHeader){
            reqHeaders["Content-Type"] =  requestContentHeader;
        }
        if(requestAcceptHeader){
            reqHeaders["Accept"] =  requestAcceptHeader;
        }
        request.Headers = reqHeaders;
        
        if(requestPayload){
            request["Body"] =  requestPayload;
        }
        logsObj.Request = request;
        
        //response json data
        var response = {}
        
        if(allResponseHeaders){
            response.Headers =  allResponseHeaders;
        }
        if(responseCode){
            response["StatusCode"] =  responseCode;
        }
        
       
        if(flowName == 'PostFlow'){
            response["Body"] =  "********";
        }
        else {
             response["Body"] =  responseBody;
        }
        logsObj.Response = response;    
        
        
        var logs = JSON.stringify(logsObj);
        context.setVariable("logs", logs);
   
}
catch (err) {
    //FlowDetails:
    var flowName = context.getVariable('current.flow.name');
    
    var applicationName = context.getVariable("developer.app.name");
    var userId = context.getVariable('userId');
    
    var logsObj = {};
    
         //application json data
        var application = {};
        if(applicationName){
            application["Name"] = applicationName;    
        }
        if(userId){
            application["UserName"] = userId;
        }
        
        logsObj.Application = application ; 
        
        //api json data
        var api = {};
        if(apiproxyName){
            api["ApiProxy"] =  apiproxyName;
        }
        if(organizationName){
            api["Organization"] =  organizationName;
        }
        if(environmentName){
            api["Environment"] =  environmentName;
        }
        if(primaryResource){
            api["FhirResource"] =  primaryResource;
        }
        logsObj.API = api;
        
        //request json data
        var request = {};
        if(proxyRequestUrl){
            request["Url"] =  proxyRequestUrl;
        }
        if(proxyVerb){
            request["Method"] =  proxyVerb;
        }
        
        var reqHeaders = {};
        if(requestContentHeader){
            reqHeaders["Content-Type"] =  requestContentHeader;
        }
        if(requestAcceptHeader){
            reqHeaders["Accept"] =  requestAcceptHeader;
        }
        request.Headers = reqHeaders;
        
        if(requestPayload){
            request["Body"] =  requestPayload;
        }
        logsObj.Request = request;
    
    logsObj.error = {};
    logsObj.error.message = err.message;
    logsObj.error.type = "LogException";
    
    var logs = JSON.stringify(logsObj);
    context.setVariable("logs", logs);
}

            


 
var proxyRequest = context.getVariable('message.uri');
var proxyVerb = context.getVariable('message.verb');
var requestPayload = context.getVariable('request.content');
var requestContentHeader = context.getVariable('request.header.Content-Type');
var scopeParam = context.getVariable('request.queryparam.scope');
if(!scopeParam){
    scopeParam = 'All';
}

var emrprofileParam = context.getVariable('request.queryparam.emrprofile');
if(!emrprofileParam){
    emrprofileParam = 'pslfhir';
}

context.setVariable('proxyRequest', proxyRequest);
context.setVariable('proxyVerb', proxyVerb);
context.setVariable('requestPayload', requestPayload);
context.setVariable('requestContentHeader', requestContentHeader);
context.setVariable('scopeParam', scopeParam);
context.setVariable('emrprofileParam', emrprofileParam);
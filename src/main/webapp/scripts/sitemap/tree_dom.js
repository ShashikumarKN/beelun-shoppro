//script generated by SiteXpert (www.xtreeme.com)
//Copyright(C) 1998-2003 Xtreeme GmbH
function addLoadHandler(lh){
if(lh){
if(!document.loadHandlers){
document.loadHandlers=new Array()
document.loadHandlers[0]=lh
document.lastLoadHandler=0}
else{
document.lastLoadHandler++
document.loadHandlers[document.lastLoadHandler]=lh}}}
addLoadHandler('p11')
addLoadHandler(window.onload)
window.onload=initializeAll
window.onunload=p10
function p15(){return true;}
window.onerror=p15
Opera=(navigator.userAgent.indexOf('Opera')!=-1)||(navigator.appName.indexOf('Opera')!=-1)||(window.opera)
IE4=(document.all&&!Opera)
mac=((IE4)&&(navigator.appVersion.indexOf("Mac")!=-1))
function p16(n01){
if(!n01)return null
var wantedInd=p01(n01)
if(n01.id.indexOf('SXP')!=-1)
wantedInd--
if(wantedInd<0)
return null
var it=n01.previousSibling
while(it){
if(it.id&&it.id.indexOf('SXP')!=-1&&p01(it)==wantedInd)
return it
it=it.previousSibling}
return null}
function p01(n01){
if(!n01.id)return-1
var end=n01.id.indexOf("SXE")
if(end==-1 || n01.id.indexOf("PH")!=-1)return-1
return parseInt(n01.id.substring(1,end))}
function p06(elm,lev,el){
if(!elm){elm="I"+lev+"SXE"+el;}
var curEl=document.getElementById(elm+"SXP")
var n01=document.getElementById(elm+"SXC")
var image=document.images ["M"+elm+"SXP"]
var image2=null
if(n02)image2=document.images ["N"+elm+"SXP"]
var bShow=(n01.style.display=="none")
var fold=document.getElementById(elm+'SXP')
if(bShow){
n01.style.display="block"
if(image)image.src=imf1
if(image2&&image2.src&&image2.src.indexOf('i2.gif')!=-1)image2.src=imf3}
else{
n01.style.display="none"
if(image)image.src=imf0
if(image2&&image2.src&&image2.src.indexOf('i3.gif')!=-1)image2.src=imf2}
var elem=n01.nextSibling
var tii=p01(n01)
while(elem){
var retVal=0
var tci=p01(elem)
if(tci!=-1){
var bfold=(elem.id.indexOf("SXP")!=-1)
if(tci==(tii+1)){
if(bfold)retVal=3
else
retVal=1}
else if(tci>(tii+1))retVal=2
else if(tci==tii&&!bfold)retVal=3
else break
if(bShow){
if(retVal==3){
elem.style.display=(elem.style.display=="none")?"block":"none"}}
else{
elem.style.display="none"
if(bfold){
var imM=document.images["M"+elem.id]
if(imM)imM.src=imf0
if(n02){
var imN=document.images["N"+elem.id]
if(imN&&imN.src&&imN.src.indexOf('i3.gif')!=-1)imN.src=imf2}}}}
elem=elem.nextSibling}
return elem}
function onExpandAll(){
var newSrc=imf1
var divColl=document.getElementsByTagName("div")
var i
for(i=0;i<divColl.length;i++){
el=divColl[i]
if(el.id&&el.id.indexOf('SX')!=-1){
el.style.display="block"
if(el.id.indexOf("SXP")!=-1){
var imM=document.images["M"+el.id]
if(imM)imM.src=newSrc
if(n02){
var imN=document.images["N"+el.id]
if(imN&&imN.src&&imN.src.indexOf('i2.gif')!=-1)imN.src=imf3}}}}}
function onCollapseAll(){
var newSrc=imf0
var divColl=document.getElementsByTagName("div")
var i
for(i=0;i<divColl.length;i++){
var el=divColl[i]
if(el.id&&el.id.indexOf('SX')!=-1){
if(el.id.indexOf("SXP")!=-1){
var imM=document.images["M"+el.id]
if(imM)imM.src=newSrc
if(n02){
var imN=document.images["N"+el.id]
if(imN&&imN.src&&imN.src.indexOf('i3.gif')!=-1)imN.src=imf2}}
if(el.id.indexOf("SXC")!=-1||p01(el)>0)el.style.display="none"}}}
function p10(){
return true}
function p11(){
if(n03)return
var nextFolder=null
var bIgnoredFolder=true
var sxpind=0
var state
var i
var tempColl=document.getElementsByTagName("div")
for(i=0;i<tempColl.length;i++){
var el=tempColl[i]
if(el.id&&el.id.indexOf('SX')!=-1){
var ii=p01(el)
if(el==nextFolder){bIgnoredFolder=true;}
if(!state||nDays==0||document.cookie.indexOf("sxpossaved")==-1){
if((el.id.indexOf("SXC")!=-1&&ii==n04)||ii>n04)el.style.display="none"}
else if(el.id.indexOf("SXP")!=-1){
var bset=false
if(bIgnoredFolder&&state.substr(sxpind,1)=="0"){
var elid=el.id.substring(0,el.id.indexOf("SXP"))
nextFolder=p06(elid)
bIgnoredFolder=false
bset=true}
if(!bset&&bIgnoredFolder){
var imM=document.images["M"+el.id]
if(imM)imM.src=imf1
if(n02){
var imN=document.images["N"+el.id]
if(imN&&imN.src&&imN.src.indexOf('i2.gif')!=-1)imN.src=imf3}}
sxpind++}}}}
function p12(el){
var retval=0
if(!el)return
if(el.id&&el.id.indexOf("SXP")!=-1){
p12(p16(el))
var ch=document.getElementById(el.id.substring(0,el.id.indexOf("SXP"))+"SXC")
if(ch&&ch.style.display=="none")p06(el.id.substring(0,el.id.indexOf("SXP")))
retval=1}
else if(el.id&&el.id.indexOf("SXC")!=-1){
p12(p16(el))
retval=1}
else{
if(el.id&&el.id.indexOf("SXR")!=-1)retval=1
if(p12(el.parentElement?el.parentElement:el.parentNode))retval=1}
return retval}
function initializeAll(){
var i
var l=document.lastLoadHandler
document.lastLoadHandler=-1
for(i=0;i<=l;i++){
var h=document.loadHandlers[i]
if(typeof(h)!='function'){
var bPar=(h.indexOf('(')==-1)
eval(h+(bPar ? '();' : ';'))}
else{
h()}}}

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@include file="../common/common.jsp"%>
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
</head>
<body>
	<!-- <video width="320" height="240" controls>
	  <source src="movie.mp4" type="video/mp4">
	  <source src="movie.ogg" type="video/ogg">
	  <source src="movie.webm" type="video/webm">
	  <object data="movie.mp4" width="320" height="240">
	    <embed src="C:\Users\Public\Videos\Sample Videos\野生动物.wmv" width="320" height="240">
	  </object> 
	</video> -->
	<!-- <video width="320" height="240" controls="controls">
		浏览器不支持
		<source src="http://localhost:8080/myFirstProject/upload/video/Wildlife.wmv">
	</video> -->
	<!-- <object type="text/html" classid="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" data="http://localhost:8080/myFirstProject/upload/video/Wildlife.wmv" width="200px" height="200px">
		<param name="filename" value="http://localhost:8080/myFirstProject/upload/video/Wildlife.wmv" />
	</object> -->
	<!-- <object id="MediaPlayer" classid="clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95" width="800" height="600" standby="Loading Windows Media Player components…" type="application/x-oleobject" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,7,1112">
	    <param name="FileName" value="test.avi">
	    <param name="AutoStart" value="true">
	    <param name="ShowControls" value="true">
	    <param name="BufferingTime" value="2">
	    <param name="ShowStatusBar" value="true">
	    <param name="AutoSize" value="true">
	    <param name="InvokeURLs" value="false">
	    <param name="AnimationatStart" value="1">
	    <param name="TransparentatStart" value="1">
	    <param name="Loop" value="1">
	    <embed type="application/x-mplayer2" src="http://localhost:8080/myFirstProject/upload/video/Wildlife.wmv" name="MediaPlayer" autostart="1" showstatusbar="1" showdisplay="1" showcontrols="1" loop="0" videoborder3d="0" pluginspage="http://www.microsoft.com/Windows/MediaPlayer/" width="800" height="600"></embed>
	  </object> -->
	  
	  <object id="NSPlay" width=200 height=180 classid="CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,5,715" standby="Loading Microsoft Windows Media Player components..." type="application/x-oleobject" align="right" hspace="5">
		<param name="AutoRewind" value=1>
		<param name="FileName" value="http://localhost:8080/myFirstProject/upload/video/Wildlife.wmv">
		<param name="ShowControls" value="1">
		<param name="ShowPositionControls" value="0">
		<param name="ShowAudioControls" value="1">
		<param name="ShowTracker" value="0">
		<param name="ShowDisplay" value="0">
		<param name="ShowStatusBar" value="0">
		<param name="ShowGotoBar" value="0">
		<param name="ShowCaptioning" value="0">
		<param name="AutoStart" value=1>
		<param name="Volume" value="-2500">
		<param name="AnimationAtStart" value="0">
		<param name="TransparentAtStart" value="0">
		<param name="AllowChangeDisplaySize" value="0">
		<param name="AllowScan" value="0">
		<param name="EnableContextMenu" value="0">
		<param name="ClickToPlay" value="0">
	</object>
</body>
</html>

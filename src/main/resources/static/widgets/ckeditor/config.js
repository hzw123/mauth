/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	//图片处理  
	config.font_names='宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;'+ config.font_names;
    config.pasteFromWordRemoveStyles = true;  
    config.filebrowserImageUploadUrl = "/compoent/uploadImages?type=image"; 
    config.toolbar = 'MyToolbar';//把默认工具栏改为‘MyToolbar’ 

    config.toolbar_MyToolbar = 
    [ 
        ['Source','Preview'], 
        ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Scayt'], 
        ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'], 
        ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'], 
        '/', 
        ['Styles','Format','Font','FontSize','TextColor'], 
        ['Bold','Italic','Strike'], 
        ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'], 
        ['Link','Unlink','Anchor'], 
        ['Maximize','-','About'] 
    ]; 
};

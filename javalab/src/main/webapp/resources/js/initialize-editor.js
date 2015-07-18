const CRIMSON_THEME = "ace/theme/crimson_editor";
const SUBLIME_THEME = "ace/theme/monokai";
const JAVA_MODE = "ace/mode/java";

function initializeAceEditorFor(editorVar, editorCode) {
    editorVar.$blockScrolling = Infinity;
    editorVar.setTheme(CRIMSON_THEME);
    editorVar.getSession().setMode(JAVA_MODE);
    editorVar.getSession().setValue(editorCode);
}

var codeEditor = ace.edit("code-editor");
initializeAceEditorFor(codeEditor, model.treedata[0].children[0].children[0].code);
<template>
  <div :id="id" style="min-height: 100vh"></div>
</template>
<script lang="ts" setup>
import jsonWorker from "monaco-editor/esm/vs/language/json/json.worker?worker";
import cssWorker from "monaco-editor/esm/vs/language/css/css.worker?worker";
import htmlWorker from "monaco-editor/esm/vs/language/html/html.worker?worker";
import tsWorker from "monaco-editor/esm/vs/language/typescript/ts.worker?worker";
import EditorWorker from "monaco-editor/esm/vs/editor/editor.worker?worker";
import * as monaco from "monaco-editor";
import { nextTick, onBeforeUnmount } from "vue";

interface MonacoProps {
  id: string; //编辑器id
  modelValue: string; // 编辑器值
  language?: string; // 语言
  readOnly?: boolean; //只读
}
const props = withDefaults(defineProps<MonacoProps>(), {
  id: "codeEditBox",
  language: "java",
  readOnly: false
});

interface MonacoEmits {
  (e: "update:modelValue", value: string): void;
}
const emit = defineEmits<MonacoEmits>();
//
// MonacoEditor start
//
onBeforeUnmount(() => {
  editor.dispose();
});

self.MonacoEnvironment = {
  getWorker(_: string, label: string) {
    if (label === "json") {
      return new jsonWorker();
    }
    if (label === "css" || label === "scss" || label === "less") {
      return new cssWorker();
    }
    if (label === "html" || label === "handlebars" || label === "razor") {
      return new htmlWorker();
    }
    if (["typescript", "javascript"].includes(label)) {
      return new tsWorker();
    }
    return new EditorWorker();
  }
};
let editor: monaco.editor.IStandaloneCodeEditor;

const editorInit = () => {
  nextTick(() => {
    monaco.languages.typescript.javascriptDefaults.setDiagnosticsOptions({
      noSemanticValidation: true,
      noSyntaxValidation: false
    });
    monaco.languages.typescript.javascriptDefaults.setCompilerOptions({
      target: monaco.languages.typescript.ScriptTarget.ES2016,
      allowNonTsExtensions: true
    });

    !editor
      ? (editor = monaco.editor.create(document.getElementById(props.id) as HTMLElement, {
          value: props.modelValue, // 编辑器初始显示文字
          language: props.language, // 语言支持自行查阅demo
          automaticLayout: true, // 自适应布局
          theme: "vs-dark", // 官方自带三种主题vs, hc-black, or vs-dark
          foldingStrategy: "indentation",
          renderLineHighlight: "all", // 行亮
          selectOnLineNumbers: true, // 显示行号
          minimap: {
            enabled: false
          },
          readOnly: props.readOnly, // 只读
          fontSize: 16, // 字体大小
          scrollBeyondLastLine: false, // 取消代码后面一大段空白
          overviewRulerBorder: false // 不要滚动条的边框
        }))
      : editor.setValue("");
    // console.log(editor)
    // 监听值的变化
    editor.onDidChangeModelContent(() => {
      emit("update:modelValue", editor.getValue());
    });
  });
};
editorInit();

/*切换语言
const changeLanguage = () => {
  monaco.editor.setModelLanguage(editor.getModel(), language.value);

  //  editor.updateOptions({
  //           language: "objective-c"
  //       });
};
//设置一个确认按钮，点击时调用接口
const submitCode = () => {
  loading.value = true;
  //调用接口
  ElMessage.success(text.value);
};*/
/***
 * editor.setValue(newValue)

editor.getValue()

editor.onDidChangeModelContent((val)=>{ //监听值的变化  })

editor.getAction('editor.action.formatDocument').run()    //格式化代码

editor.dispose()    //销毁实例

editor.onDidChangeOptions　　//配置改变事件

editor.onDidChangeLanguage　　//语言改变事件
 */
</script>

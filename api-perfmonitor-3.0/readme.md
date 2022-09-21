当前版本：3.0-release
功能：1、代码生成器
     2、性能监控：
          A、执行trace ，支持异常
          B、性能消耗
          C、GC变化
更新内容：字节码ASM 使用 tree API 替换 code API 
解决问题：
   1、 因字节码编写格式问题，造成的异常 以及 可能的隐藏问题。
   2、 无需目标程序添加-noverify

PS：目标程序需要添加asm9.2相关 jar（针对鼎捷DAP架构）；代码增强后对其进行CheckClassAdapters会有 内部类加载问题。但是经过验证不影响正常执行监控。
部署对象和流程定义相关的表
ACT_RE_DEPLOYMENT  #部署对象表
ACT_RE_PROCDEF #流程定义表
ACT_GE_BYTEARRAY #资源文件表 （blob 类型存储图片和.bpmn）
ACT_GE_PROPERTY #主键生成策略表


ACT_RU_EXECUTION 正在执行的对对象
ACT_RU_TASK 正在执行的任务（只有图标是UserTask类型的才能有数据）
ACT_HI_TASKINST 任务历史（只有图标是UserTask类型的才能有数据）
ACT_HI_ACTINST 流程流转历史（包含UserTask类型和其他类型）
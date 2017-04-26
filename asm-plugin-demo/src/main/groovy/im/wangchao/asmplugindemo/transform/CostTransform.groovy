package im.wangchao.asmplugindemo.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import im.wangchao.asmdemo.CostClassVisitor
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

/**
 * <p>Description  : CostTransform.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 17/4/24.</p>
 * <p>Time         : 下午3:13.</p>
 */
class CostTransform extends Transform{
    Project project

    CostTransform(Project project){
        this.project = project
    }

    @Override String getName() {
        return "MotCostTransform"
    }

    @Override Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override boolean isIncremental() {
        return false
    }

    @Override void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        def inputs = transformInvocation.getInputs()
        def outputProvider = transformInvocation.getOutputProvider()

        // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
        inputs.each { TransformInput input ->
            //对类型为“文件夹”的input进行遍历
            input.directoryInputs.each {DirectoryInput directoryInput->
                //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等

                if (directoryInput.file.isDirectory()){
                    directoryInput.file.eachFileRecurse { File file ->
                        def name = file.name
                        project.logger.error("File name: ${name}")

                        if (name.endsWith(".class")
                                && !name.startsWith("R\$")
                                && !name.equals("R.class")
                                && !name.equals("BuildConfig.class")){
                            project.logger.error("inject >>> ${name}")

                            ClassReader cr = new ClassReader(file.bytes)
                            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
                            ClassVisitor cv = new CostClassVisitor(Opcodes.ASM5, cw, project)

                            cr.accept(cv, ClassReader.EXPAND_FRAMES)

                            byte[] code = cw.toByteArray()

                            FileOutputStream fos = new FileOutputStream(file.parentFile.absolutePath + File.separator + name)
                            fos.write(code)
                            fos.close()
                        }

                    }
                }

                // 获取output目录
                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)

                project.logger.error("dir dest: ${dest}")

                // 将input的目录复制到output指定目录
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            //对类型为jar文件的input进行遍历
            input.jarInputs.each {JarInput jarInput->

                //jar文件一般是第三方依赖库jar文件

                // 重命名输出文件（同目录copyFile会冲突）
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if(jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0,jarName.length()-4)
                }
                //生成输出路径
                def dest = outputProvider.getContentLocation(jarName+md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)

                project.logger.error("jar dest: ${dest}")

                //将输入内容复制到输出
                FileUtils.copyFile(jarInput.file, dest)
            }
        }
    }
}

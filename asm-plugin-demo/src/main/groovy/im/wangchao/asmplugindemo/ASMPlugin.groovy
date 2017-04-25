package im.wangchao.asmplugindemo

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import im.wangchao.asmplugindemo.transform.CostTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * <p>Description  : ASMPlugin.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 17/4/24.</p>
 * <p>Time         : 下午2:30.</p>
 */
class ASMPlugin implements Plugin<Project>{

    @Override void apply(Project project) {

        if (project.plugins.hasPlugin(AppPlugin)){
            AppExtension android = project.extensions.getByType(AppExtension)
            android.registerTransform(new CostTransform(project))
        }
    }
}

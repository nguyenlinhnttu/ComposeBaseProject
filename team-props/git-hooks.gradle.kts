fun isLinuxOrMacOs(): Boolean {
    return isMac() || isLinux()
}

fun isLinux(): Boolean {
    return System.getProperty("os.name").toLowerCase().contains("linux")
}

fun isMac(): Boolean {
    val osName = System.getProperty("os.name").toLowerCase()
    return osName.contains("mac os")
            || osName.contains("macos")
}

tasks.register("copyGitHooks", Copy::class) {
    description = "Copies the git hooks from team-props/git-hooks to the .git folder."
    val path = if (isLinux()) "$rootDir/team-props/git-hooks/linux"
    else "$rootDir/team-props/git-hooks/mac" // diff paths from Linux & Mac environment
    from(path) {
        include("**/*.sh")
        rename("(.*).sh", "$1")
    }
    into("$rootDir/.git/hooks")
    onlyIf { isLinuxOrMacOs() }
}

tasks.register("installGitHooks", Exec::class) {
    description = "Installs the pre-commit git hooks from team-props/git-hooks."
    group = "git hooks"
    workingDir = rootDir
    setCommandLine("chmod", "-R", "+x", ".git/hooks/")
    dependsOn("copyGitHooks")
    onlyIf { isLinuxOrMacOs() }
    doLast {
        logger.info("Git hook installed successfully!")
    }
}

afterEvaluate {
    tasks["clean"].dependsOn("installGitHooks")
}
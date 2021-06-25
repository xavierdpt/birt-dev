package p;

import java.util.Optional;

public class MavenPlugin {

    private String groupId;
    private String artifactId;
    private String version;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {

        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getKey() {
        return emptyIfNull(groupId) + ":" + emptyIfNull(artifactId) + ":" + emptyIfNull(version);
    }

    private Object emptyIfNull(String str) {
        return str == null ? "" : str;
    }

    public String getDescribeCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("mvn help:describe");
        if (groupId != null) {
            Optional.ofNullable(groupId).ifPresent(s -> sb.append(" -DgroupId=" + s));
            Optional.ofNullable(artifactId).ifPresent(s -> sb.append(" -DartifactId=" + s));
        } else {
            sb.append(" -Dplugin=" + artifactId.replaceFirst("maven-", "").replaceFirst("-plugin", ""));
        }
        Optional.ofNullable(version).ifPresent(s -> sb.append(" -Dversion=" + s));
        sb.append(" -Ddetail");
        sb.append(" -l " + getKey() + ".help");
        return sb.toString();
    }
}

/**
 * WitherBlocker - Package: syam.witherblocker.permission
 * Created: 2012/10/28 15:54:34
 */
package syam.witherblocker.permission;

import org.bukkit.permissions.Permissible;

/**
 * Perms (Perms.java)
 * @author syam(syamn)
 */
public enum Perms {
    /* Permission Nodes */
    // Admin permission
    ALLOW ("allow"),
    ;

    // Node header
    final String HEADER = "witherblocker.";
    private String node;

    Perms(final String node){
        this.node = HEADER + node;
    }

    // Simply permission checker
    public boolean has(final Permissible perm){
        if (perm == null) return false;
        return perm.hasPermission(node);
    }

    public String getPermissionNode(){
        return node;
    }
}

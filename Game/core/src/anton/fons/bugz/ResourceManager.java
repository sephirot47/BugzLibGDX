package anton.fons.bugz;

import com.badlogic.gdx.assets.AssetManager;

import java.util.ArrayList;
import java.util.HashMap;

import anton.fons.bugz.SceneGraph.Scene;

public class ResourceManager
{
    private AssetManager assetManager;
    private HashMap< Scene, ArrayList<String> > map;

    public ResourceManager()
    {
        assetManager = new AssetManager();
        map = new HashMap<Scene, ArrayList<String>>();
    }

    public void update()
    {
        assetManager.update();
    }

    public <T> void load(String path, Class<T> resourceClass, Scene scene)
    {
        if(map.get(scene) == null)
            map.put(scene, new ArrayList<String>());

        map.get(scene).add(path);
        assetManager.load(path, resourceClass);
    }

    public <T> T get(String path, Class<T> resourceClass)
    {
        return assetManager.get(path, resourceClass);
    }

    public boolean sceneLoaded(Scene scene)
    {
        if(!map.containsKey(scene)) return true;

        for(String id : map.get(scene))
        {
            if( !assetManager.isLoaded(id) ) return false;
        }
        return true;
    }
}

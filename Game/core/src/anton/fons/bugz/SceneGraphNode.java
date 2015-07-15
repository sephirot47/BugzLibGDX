package anton.fons.bugz;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

import java.util.ArrayList;

public class SceneGraphNode
{
    private SceneGraphNode parent;
    private ArrayList<SceneGraphNode> childs;

    public SceneGraphNode()
    {
        childs = new ArrayList<SceneGraphNode>();
    }

    public void _create()
    {
        for(SceneGraphNode child : childs) child._create();
    }

    public void _update()
    {
        for(SceneGraphNode child : childs) child._update();
    }

    public void _loadAssets()
    {

    }

    public void _onAssetsLoaded()
    {

    }

    public void _render(ModelBatch modelBatch, Environment environment)
    {

    }

    //Overridable functions /////////////////////////////////////////////////////
        protected void create() {}
        protected void loadAssets() {}
        protected void onAssetsLoaded() {}
        protected void update(float deltaTime) {}
        protected void render(ModelBatch modelBatch, Environment environment) {}
    ////////////////////////////////////////////////////////////////////////////

    //Child and parent related functions ////////////////////////////////////////
        void addChild(SceneGraphNode child) { childs.add(child); }
        void removeChild(SceneGraphNode child) { childs.remove(child); }

        public SceneGraphNode getParent() { return parent; }
        public ArrayList<SceneGraphNode> getChilds() { return childs; }
    ////////////////////////////////////////////////////////////////////////////

    // MISC /////////////////////////////////////////////////////////////////////
    public AssetManager getAssetManager() { return parent.getAssetManager(); }
    /////////////////////////////////////////////////////////////////////////////
}

package anton.fons.bugz;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class SceneGraphNode
{
    private SceneGraphNode parent;
    private ArrayList<SceneGraphNode> children;

    private Vector3 position, scale;
    private Quaternion rotation;

    public SceneGraphNode()
    {
        children = new ArrayList<SceneGraphNode>();

        position = new Vector3(0f, 0f, 0f);
        rotation = new Quaternion();
        scale = new Vector3(1f, 1f, 1f);
    }

    public final void _create()
    {
        create();
        for(SceneGraphNode child : children) child._create();

        _loadAssets(); //LOAD THE ASSETS!
    }

    public final void _loadAssets()
    {
        loadAssets();
        //No need to call _loadAssets on childs, _create does it
    }

    public final void _onAssetsLoaded()
    {
        onAssetsLoaded();
        for(SceneGraphNode child : children) child._onAssetsLoaded();
    }

    public final void _update(float deltaTime)
    {
        boolean assetsLoaded = assetsLoaded();
        if(!assetsLoaded) //Keep track of the assets being load, on every _update
        {
            if (!getAssetsManager().update()) return; //If it's still loading...
            else if (!assetsLoaded)  _onAssetsLoaded(); //Assets have just loaded, must be processed
        }

        if(assetsLoaded)
        {
            //Game.AndroidResolver.log("_update " + this);
            update(deltaTime);
            for (SceneGraphNode child : children) child._update(deltaTime);
        }
    }

    public final void _render(ModelBatch modelBatch, Environment environment)
    {
        if(!assetsLoaded()) return;

        prerender(modelBatch, environment);
        for(SceneGraphNode child : children) child._render(modelBatch, environment);
        postrender(modelBatch, environment);
    }

    public final void _dispose()
    {
        for(SceneGraphNode child : children) child._dispose();
        dispose();
    }

    public final void _pause()
    {
        for(SceneGraphNode child : children) child._pause();
        pause();
    }

    public final void _resume()
    {
        for(SceneGraphNode child : children) child._resume();
        resume();
    }

    public final void _resize(int width, int height)
    {
        if(!assetsLoaded()) return;

        for(SceneGraphNode child : children) child._resize(width, height);
        resize(width, height);
    }


    //Overridable functions /////////////////////////////////////////////////////
        protected void create() { }
        protected void loadAssets() {}
        protected void onAssetsLoaded() {}
        protected void update(float deltaTime) {}
        protected void pause() {}
        protected void resume() {}
        protected void prerender(ModelBatch modelBatch, Environment environment) {}
        protected void postrender(ModelBatch modelBatch, Environment environment) {}
        protected void resize(int width, int height) {}
        protected void dispose() {}
    ////////////////////////////////////////////////////////////////////////////

    //Child and parent related functions ////////////////////////////////////////
        public final void addChild(SceneGraphNode child)
        {
            children.add(child);
            child.parent = this;
        }

        public final void removeChild(SceneGraphNode child)
        {
            children.remove(child);
            child.parent = null;
        }

        public SceneGraphNode getParent() { return parent; }
        public final ArrayList<SceneGraphNode> getChildren() { return children; }
    ////////////////////////////////////////////////////////////////////////////

    // Transform ///////////////////////////////////////////////////////////////

        //SCALE
        public void setScale(float scale) { this.scale = new Vector3(scale, scale, scale); }
        public void setScale(Vector3 scale) { this.scale = scale; }

        //ROTATE
        public void setRotation(float axisX, float axisY, float axisZ, float degrees)
        {
            rotation.setFromAxis(axisX, axisY, axisZ, degrees);
        }
        public void setRotation(Vector3 axis, float degrees)
        {
            rotation.setFromAxis(axis, degrees);
        }
        public void rotate(float axisX, float axisY, float axisZ, float degrees)
        {
            rotate(new Vector3(axisX, axisY, axisZ), degrees);
        }
        public void rotate(Vector3 axis, float degrees)
        {
            Quaternion rot = new Quaternion();
            rot.setFromAxis(axis, degrees);
            rotation.mul(rot);
        }

        //TRANSLATE
        public void setPosition(float x, float y, float z) { this.position = new Vector3(x,y,z); }
        public void setPosition(Vector3 pos) { this.position = new Vector3(pos.x, pos.y, pos.z); }
        public final Matrix4 getTransformMatrix()
        {
            Matrix4 transform = new Matrix4();
            transform.translate(position.x, position.y, position.z);
            transform.rotate(rotation);
            transform.scale(scale.x, scale.y, scale.z);
            return transform;
        }
    ////////////////////////////////////////////////////////////////////////////

    // MISC /////////////////////////////////////////////////////////////////////
        public AssetManager getAssetsManager()
        {
            return parent == null ? null : parent.getAssetsManager();
        }

        protected boolean assetsLoaded() { return parent.assetsLoaded(); }
    /////////////////////////////////////////////////////////////////////////////
}

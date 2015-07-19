package anton.fons.bugz.SceneGraph;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class SceneGraphNode
{
    protected boolean affectedByLight = true;

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

    public void _create()
    {
        create();
        for(SceneGraphNode child : children) child._create();
    }

    public void _update(float deltaTime)
    {
        update(deltaTime);
        for (SceneGraphNode child : children) child._update(deltaTime);
    }

    public final void _render(ModelBatch modelBatch, Environment environment)
    {
        prerender(modelBatch, environment);
        for (SceneGraphNode child : children) child._render(modelBatch, environment);
        postrender(modelBatch, environment);
    }

    public final void _dispose()
    {
        for(SceneGraphNode child : children) child._dispose();
        dispose();
        if(parent != null) parent.removeChild(this);
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
        for(SceneGraphNode child : children) child._resize(width, height);
        resize(width, height);
    }


    //Overridable functions /////////////////////////////////////////////////////
        protected void create() { }
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
            child._create();
        }

        public final boolean hasChild(SceneGraphNode child)
        {
            return children.contains(child);
        }

        public final void removeChild(SceneGraphNode child)
        {
            children.remove(child);
            child.parent = null;
        }

        public final void removeAllChildren()
        {
            //Remove all children
            while(children.size() > 0)
            {
                SceneGraphNode child = children.get(0);
                child._dispose();
                removeChild(child);
            }
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

        public Vector3 getPosition() { return position; }
        public Vector3 getScale() { return scale; }
        public Quaternion getRotation() { return rotation; }


        public Vector3 getGlobalPosition()
        {
            Vector3 p = new Vector3();
            getGlobalTransform().getTranslation(p);
            return p;/*
            Vector3 parentPos = parent == null ? position : parent.getGlobalPosition();
            Vector3 sum = new Vector3(parentPos.x + position.x,
                    parentPos.y + position.y,
                    parentPos.z + position.z);
            return sum;*/
        }

        public Vector3 getGlobalScale()
        {
            Vector3 s = new Vector3();
            getGlobalTransform().getScale(s);
            return s;
        }

        public Quaternion getGlobalRotation()
        {
            if(parent == null) return rotation;
            Quaternion pRot = parent.getGlobalRotation();
            Quaternion parRot = new Quaternion(pRot.x, pRot.y, pRot.z, pRot.w);
            Quaternion thisRot = new Quaternion(rotation.x, rotation.y, rotation.z, rotation.w);
            return parRot.mul(thisRot);
        }

        //TRANSFORM
        public Matrix4 getGlobalTransform()
        {
            Matrix4 local = getLocalTransform();
            if (parent == null) return local;
            Matrix4 global = parent.getGlobalTransform();
            Matrix4.mul(global.val, local.val);
            return global;
        }

        public final Matrix4 getLocalTransform()
        {
            Matrix4 transform = new Matrix4();
            transform.translate(position.x, position.y, position.z);
            transform.rotate(rotation);
            transform.scale(scale.x, scale.y, scale.z);
            return transform;
        }
    ////////////////////////////////////////////////////////////////////////////

    // MISC /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
}

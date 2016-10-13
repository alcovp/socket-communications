package com.alc.rendering;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by alc on 03.10.2015.
 */
public class Renderer {
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback mouseMotionCallback;
    private GLFWScrollCallback scrollCallback;
    private GLFWFramebufferSizeCallback resizeCallback;
    private final Cache cache;
    private long window;
    private List<RendererObject> objects;
    private Shader shader;
    private int width;
    private int height;
    private String title;
    private final AbstractCamera camera;
    private List<Light> lights;
    private AmbientLight ambient = new AmbientLight(new Vector3f(0.1f, 0.1f, 0.1f));
    private final String texturesPath;
    private final String modelsPath;

    private Matrix4f scaleBiasMatrix = new Matrix4f(
            0.5f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.5f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f
    );
    private Model hoodModel = new Model(
            new Vertex[]{
                    new Vertex(new Vector3f(-1, -1, 0), new Vector2f(0, 0), new Vector3f(0, 0, 1)),
                    new Vertex(new Vector3f(-1, -0.5f, 0), new Vector2f(0, 1), new Vector3f(0, 0, 1)),
                    new Vertex(new Vector3f(-0.5f, -0.5f, 0), new Vector2f(1, 1), new Vector3f(0, 0, 1)),
                    new Vertex(new Vector3f(-0.5f, -1, 0), new Vector2f(1, 0), new Vector3f(0, 0, 1))
            },
            new int[]{
                    2, 1, 0, 0, 3, 2
            }
    );
    private int depthTextureSize = 1024;
    private int depthTextureId;
    private int framebufferID;

    private int depthTextureSlot = 0;
    private int colorTextureSlot = 1;

    private Vector2f nearFarPlanes = new Vector2f(1, 100);

    public Renderer(GLFWKeyCallback keyCallback, GLFWCursorPosCallback mouseMotionCallback, GLFWScrollCallback scrollCallback, int width, int height, String title, AbstractCamera camera, String texturesPath, String modelsPath) {

        this.keyCallback = keyCallback;
        this.mouseMotionCallback = mouseMotionCallback;
        this.scrollCallback = scrollCallback;
        this.width = width;
        this.height = height;
        this.title = title;
        this.camera = camera;
        this.objects = new ArrayList<>();
        this.lights = new ArrayList<>();
        this.cache = new Cache();
        this.texturesPath = texturesPath;
        this.modelsPath = modelsPath;
    }

    public void run() {

        try {
            init();
            loop();

            // Release window and window callbacks
            glfwDestroyWindow(window);
            keyCallback.release();
            errorCallback.release();
            resizeCallback.release();
            mouseMotionCallback.release();
            scrollCallback.release();
        } finally {
            // Terminate GLFW and release the GLFWerrorfun
            glfwTerminate();
            errorCallback.release();
        }
        System.exit(0);
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (glfwInit() != GL11.GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback);
        glfwSetCursorPosCallback(window, mouseMotionCallback);
        glfwSetScrollCallback(window, scrollCallback);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                window,
                (GLFWvidmode.width(vidmode) - width) / 2,
                (GLFWvidmode.height(vidmode) - height) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(0);

        // Make the window visible
        glfwShowWindow(window);

        resizeCallback = new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                glViewport(0, 0, width, width);
            }
        };
        glfwSetFramebufferSizeCallback(window, resizeCallback);
    }

    private void loop() {

        setup();

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (glfwWindowShouldClose(window) == GL_FALSE) {

            draw();
            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
        dispose();
    }

    private void draw() {

        Matrix4f lightViewProjectionMatrix = new Matrix4f()
                .setPerspective((float) Math.toRadians(45), width / height, nearFarPlanes.x, nearFarPlanes.y)
                .lookAt(
                        lights.get(0).getPosition(),
                        new Vector3f(0, 0, 0),
                        new Vector3f(0, 1, 0)
                );

        Matrix4f shadowMatrix = new Matrix4f(scaleBiasMatrix).mul(lightViewProjectionMatrix);

        glBindFramebuffer(GL_FRAMEBUFFER, framebufferID);
        glDrawBuffer(GL_NONE);
        glReadBuffer(GL_NONE);
        shader.useShadow();
        glActiveTexture(GL_TEXTURE0 + depthTextureSlot);
        glBindTexture(GL_TEXTURE_2D, 0);
        glActiveTexture(GL_TEXTURE0 + colorTextureSlot);
        glBindTexture(GL_TEXTURE_2D, 0);
        glViewport(0, 0, depthTextureSize, depthTextureSize);
        glClearDepth(1.0f);
        glEnable(GL_POLYGON_OFFSET_FILL);
        glPolygonOffset(2.0f, 4.0f);
        glClear(GL_DEPTH_BUFFER_BIT);
        for (RendererObject object : objects) {
            Matrix4f modelMatrix = new Matrix4f()
                    .translationRotateScale(object.getPosition(), new Quaternionf()/*object.getDirection()*/, object.getScale());
            Matrix4f MVP = new Matrix4f(lightViewProjectionMatrix).mul(modelMatrix);
            shader.setUniform("mvps", MVP);
            cache.getModels().get(object.getModelId()).draw();
        }


        Matrix4f viewProjectionMatrix = new Matrix4f()
                .setPerspective((float) Math.toRadians(45), width / height, 0.01f, 100)
                .lookAt(
                        camera.getEye(),
                        camera.getCenter(),
                        camera.getUp()
                );
        glDisable(GL_POLYGON_OFFSET_FILL);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glDrawBuffer(GL_BACK);
        glReadBuffer(GL_BACK);

        glViewport(0, 0, width, width);
        clear();
        shader.use();
        shader.setUniform("ambient", ambient.getColor());
        shader.setUniform("lightP", lights.get(0).getPosition());
        shader.setUniform("lightC", lights.get(0).getColor());
        shader.setUniform("lightA", lights.get(0).getAttenuation());
        shader.setUniform("eye", camera.getEye());
        shader.setUniform("shadow_matrix", shadowMatrix);
        shader.setUniform("depth_texture", depthTextureSlot);
        shader.setUniform("tex", colorTextureSlot);

        glActiveTexture(GL_TEXTURE0 + depthTextureSlot);
        glBindTexture(GL_TEXTURE_2D, depthTextureId);
        glActiveTexture(GL_TEXTURE0 + colorTextureSlot);
        for (RendererObject object : objects) {
            Matrix4f modelMatrix = new Matrix4f()
                    .translationRotateScale(object.getPosition(), new Quaternionf()/*object.getDirection()*/, object.getScale());
            Matrix4f MVP = new Matrix4f(viewProjectionMatrix).mul(modelMatrix);
            shader.setUniform("mvp", MVP);
            shader.setUniform("model", modelMatrix);
            shader.setUniform("shine", object.getMaterial().getShine());
            shader.setUniform("coloring", object.getMaterial().getColor());
            cache.getTextures().get(object.getTextureId()).use();
            cache.getModels().get(object.getModelId()).draw();
        }

        shader.useShadowTest();
        Matrix4f viewOrthoMatrix = new Matrix4f()
                .setOrtho(-1, 1, -1, 1, -1, 1);
        shader.setUniform("mvpt", viewOrthoMatrix);
        glBindTexture(GL_TEXTURE_2D, depthTextureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_MODE, GL_NONE);
        hoodModel.draw();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_MODE, GL_COMPARE_REF_TO_TEXTURE);

        FPSCounter.increment();
    }

    private void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    private void setup() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        //GL.createCapabilities(); // valid for latest build
        GLContext.createFromCurrent(); // use this line instead with the 3.0.0a build

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0f);
        glViewport(0, 0, width, width);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);

        this.shader = new Shader();

        framebufferID = glGenFramebuffers();                                         // create a new framebuffer

//        framebufferID = glGenFramebuffers();                                         // create a new framebuffer
//        glBindFramebuffer(GL_FRAMEBUFFER, framebufferID);                        // switch to the new framebuffer
//        glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_CUBE_MAP_POSITIVE_X, 0); // attach it to the framebuffer
//
//        depthRenderBufferID = glGenRenderbuffersEXT();                                  // And finally a new depthbuffer
//
//        // initialize depth renderbuffer
//        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthRenderBufferID);                // bind the depth renderbuffer
//        glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH_COMPONENT, 512, 512); // get the data space for it
//        glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, depthRenderBufferID); // bind it to the renderbuffer
//
//        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);


        depthTextureId = glGenTextures();                                               // and a new texture used as a color buffer

        // initialize color texture
        glBindTexture(GL_TEXTURE_2D, depthTextureId);                                   // Bind the colorbuffer texture
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT32, depthTextureSize, depthTextureSize, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (java.nio.ByteBuffer) null);  // Create the texture data
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);               // make it linear filterd
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);               // make it linear filterd
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_MODE, GL_COMPARE_REF_TO_TEXTURE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_FUNC, GL_LEQUAL);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glBindTexture(GL_TEXTURE_2D, 0);

        glBindFramebuffer(GL_FRAMEBUFFER, framebufferID);                        // switch to the new framebuffer
        glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, depthTextureId, 0); // attach it to the framebuffer

        // initialize color texture
//        glBindTexture(GL_TEXTURE_CUBE_MAP, depthTextureId);                                   // Bind the colorbuffer texture
//        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
//        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
//        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
////        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_DEPTH_TEXTURE_MODE, GL_LUMINANCE);
//        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_COMPARE_MODE, GL_COMPARE_REF_TO_TEXTURE);
//        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_COMPARE_FUNC, GL_LEQUAL);
//        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//        for (int i = 0; i < 6; i++) {
//            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_DEPTH_COMPONENT32, depthTextureSize, depthTextureSize, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (java.nio.ByteBuffer) null);
//        }
//        glBindTexture(GL_TEXTURE_2D, 0);
//
//        glBindFramebuffer(GL_FRAMEBUFFER, framebufferID);                        // switch to the new framebuffer
//        for (int i = 0; i < 6; i++) {
////            glFramebufferTextureLayer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, depthTextureId, 0, i); // attach it to the framebuffer
////            glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, depthTextureId, 0);
//        }


        // initialize depth renderbuffer
//        glBindRenderbuffer(GL_RENDERBUFFER, depthRenderBufferID);                // bind the depth renderbuffer
//        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, depthTextureSize, depthTextureSize); // get the data space for it
//        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, depthRenderBufferID); // bind it to the renderbuffer

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    private void dispose() {
        cache.getModels().forEach((s, model) -> model.dispose());
        cache.getTextures().forEach((s, texture) -> texture.dispose());
        shader.dispose();
    }

    public List<RendererObject> getObjects() {
        return objects;
    }

    public void setObjects(List<RendererObject> objects) {
        this.objects = objects;
        for (RendererObject object : objects) {
            if (!cache.getModels().containsKey(object.getModelId())) {
                cache.getModels().put(object.getModelId(), ResourceLoader.loadModel(modelsPath, object.getModelId()));
            }
            if (!cache.getTextures().containsKey(object.getTextureId())) {
                cache.getTextures().put(object.getTextureId(), ResourceLoader.loadTexture(texturesPath, object.getTextureId()));
            }
        }
    }

    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }
}

package engine.gfx;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public final class LinMath {
	public static Matrix4f getProjectionMatrix(final float WIDTH, final float HEIGHT,
			final float FOV, final float NEAR, final float FAR) {
		float aspc = WIDTH/(float)HEIGHT;
		float invTan = 1f/(float)Math.tan(Math.toRadians(FOV/2));
		float frustum = FAR-NEAR;
		return new Matrix4f(
				invTan/aspc, 0, 0, 0,
				0, invTan, 0, 0,
				0, 0, -(FAR+NEAR)/frustum, -1,
				0, 0, -(2*FAR*NEAR)/frustum, 0);
	}
	
	public static Matrix4f createTransformationMatrix(Vector3f pos, Vector3f rotation, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.translate(pos);
		matrix.rotate(rotation.x, new Vector3f(1,0,0), matrix);
		matrix.rotate(rotation.y, new Vector3f(0,1,0), matrix);
		matrix.rotate(rotation.z, new Vector3f(0,0,1), matrix);
		matrix.scale(new Vector3f(scale,scale,scale));
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix);
        viewMatrix.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix);
        viewMatrix.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0, 0, 1), viewMatrix);
        Vector3f cameraPos = camera.getPos();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        viewMatrix.translate(negativeCameraPos);
        return viewMatrix;
    }
	
	public static Matrix4f multiply(Matrix4f x, Matrix4f y){
		Vector4f[] a = new Vector4f[4];
		Vector4f[] b = new Vector4f[4];
		for(int i = 0; i < 4; i++) {
			x.getRow(i, a[i] = new Vector4f());
			y.getColumn(i, b[i] = new Vector4f());
		}
		return new Matrix4f(
				a[0].dot(b[0]),	a[1].dot(b[0]),	a[2].dot(b[0]),	a[3].dot(b[0]),
				a[0].dot(b[1]),	a[1].dot(b[1]),	a[2].dot(b[1]),	a[3].dot(b[1]),
				a[0].dot(b[2]),	a[1].dot(b[2]),	a[2].dot(b[2]),	a[3].dot(b[2]),
				a[0].dot(b[3]),	a[1].dot(b[3]),	a[2].dot(b[3]),	a[3].dot(b[3]));	
	}
}

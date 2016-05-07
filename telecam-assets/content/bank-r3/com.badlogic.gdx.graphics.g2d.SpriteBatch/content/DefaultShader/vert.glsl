attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
varying vec4 v_color;
varying vec2 v_texCoords;

float trunc(float value) {
	float result = value;
//	float result = value * (255.0 / 254.0);
	float limit = 1.0;
	if (value > limit) {
		result = limit;
	}
	return result;
}
void main() {
	v_color = a_color;
	v_color.a = v_color.a * (255.0 / 254.0);
//	float a = v_color.a;
//	float r = v_color.r;
//	float g = v_color.g;
//	float b = v_color.b;
//
//	v_color.a = trunc(a);
//	v_color.r = trunc(r);
//	v_color.g = trunc(g);
//	v_color.b = trunc(b);

	v_texCoords = a_texCoord0;
	gl_Position = u_projTrans * a_position;
}


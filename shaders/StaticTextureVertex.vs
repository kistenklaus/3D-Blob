#version 400 core

in vec3 position;
in vec2 textureCoord;

uniform mat4 screen;
uniform mat4 trans;

out vec2 uv;

void main(){
	uv = textureCoord;
	gl_Position = screen * trans * vec4(position,1);
}
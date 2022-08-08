#version 400 core

in vec2 uv;

uniform sampler2D sampler;

out vec4 fcolor;

void main(){
	fcolor = texture(sampler, uv);
}
#version 400 core

in vec3 position;
in vec3 normal;

uniform mat4 transform;
uniform mat4 view;
uniform mat4 projection;

uniform vec3 lightPosition;

out vec3 fragNormal;
out vec3 toLightVector;

void main(void){
    vec4 worldPosition = transform* vec4(position,1.0);
    fragNormal = (transform*vec4(normal,0.0)).xyz;
    toLightVector = lightPosition-worldPosition.xyz;
    gl_Position = projection* view* worldPosition;
}
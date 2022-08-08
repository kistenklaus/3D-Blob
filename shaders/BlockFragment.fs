#version 400 core

in vec3 fragNormal;
in vec3 toLightVector;

uniform vec3 color;
uniform vec3 lightColor;
uniform vec3 ambientLight;

out vec4 fragColor;

void main(void){
    vec3 unitNormal = normalize(fragNormal);
    vec3 unitToLight = normalize(toLightVector);
    float nDotl = dot(unitNormal, unitToLight);
    float brightness = max((nDotl+0.75)/2.0,0.1);
    vec3 diffuse = brightness * lightColor;
    fragColor = vec4(diffuse*color,1.0);
}
FROM nginx:1.17.1-alpine
COPY ./dist/neuroLab /usr/share/nginx/html

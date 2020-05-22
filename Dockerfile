FROM node:12.16.2-alpine AS build
WORKDIR /usr/src/app
COPY package.json ./
RUN npm install
COPY . .
RUN npm run build


FROM nginx:1.17.1-alpine
COPY --from=build /usr/src/app/dist/neuroLab /usr/share/nginx/html

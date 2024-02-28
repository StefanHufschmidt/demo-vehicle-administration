FROM node:21.6.2 AS build
WORKDIR /tmp/web-frontend/
ADD package.json /tmp/web-frontend/package.json
ADD package-lock.json /tmp/web-frontend/package-lock.json
RUN npm install
RUN npm run build

FROM nginxinc/nginx-unprivileged:1.25.4 AS run
COPY --from=build /tmp/web-frontend/build/* /usr/share/nginx/web-frontend/

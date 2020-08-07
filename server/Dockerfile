FROM node:lts

# Create app directory
WORKDIR /usr/app
COPY package*.json ./

RUN npm install
RUN npm update
RUN npm audit fix

EXPOSE 3000
COPY . .
#CMD ["npm", "start"]

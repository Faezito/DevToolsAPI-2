FROM node:22-bookworm

WORKDIR /app

COPY package*.json ./

RUN npm install --legacy-peer-deps

COPY . .

RUN chmod +x generate-config.sh

EXPOSE 4200

ENTRYPOINT ["./generate-config.sh"]

CMD ["npm", "start", "--", "--host", "0.0.0.0", "--poll", "1000"]
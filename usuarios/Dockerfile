FROM mcr.microsoft.com/dotnet/sdk:6.0
WORKDIR /app

COPY . .
RUN dotnet build

# Establece el puerto del servidor web
ENV ASPNETCORE_KESTREL__ENDPOINTS__HTTP__URL=http://+:5200

CMD ["dotnet", "run"]
EXPOSE 5200
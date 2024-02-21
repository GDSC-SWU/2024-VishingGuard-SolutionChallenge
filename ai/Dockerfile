
FROM python:3.11.1

WORKDIR /app

COPY . /app

RUN pip install -r requirements.txt

CMD ["uvicorn", "main:app", "--reload", "--host", "0.0.0.0", "--port", "8081"]


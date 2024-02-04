from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from transformers import AutoModelForSequenceClassification, AutoTokenizer
import torch

app = FastAPI()

# 모델 및 토크나이저 불러오기
model_name = "chaeyeon1/vp_kobert_model"
model = AutoModelForSequenceClassification.from_pretrained(model_name)
tokenizer = AutoTokenizer.from_pretrained(model_name)

class PredictInput(BaseModel):
    text: str  # 입력 텍스트

class PredictOutput(BaseModel):
    is_phishing: bool  # 보이스피싱 여부

@app.post("/predict", response_model=PredictOutput)
async def predict(payload: PredictInput):
    try:
        # 텍스트를 토큰화하고 모델에 입력으로 전달
        inputs = tokenizer(payload.text, return_tensors='pt', padding=True, truncation=True)
        with torch.no_grad():
            outputs = model(**inputs)

        logits = outputs.logits
        probabilities = torch.nn.functional.softmax(logits, dim=1).squeeze().tolist()

        # 확률에 따라 보이스피싱 여부 판단
        is_phishing = probabilities[1] > 0.5  # 보이스피싱 클래스의 확률이 0.5 이상인 경우 True

        return {"is_phishing": is_phishing}

    except Exception as e:
        print(f"에러 발생: {str(e)}")
        raise HTTPException(status_code=500, detail=f"내부 서버 오류: {str(e)}")

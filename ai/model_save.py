# 모델 및 토크나이저 불러오기
model_name = "chaeyeon1/vp_kobert_model"
model = AutoModelForSequenceClassification.from_pretrained(model_name)
tokenizer = AutoTokenizer.from_pretrained(model_name)

import os
import librosa
import soundfile as sf
from librosa.effects import preemphasis, trim
import numpy as np

def preprocess_audio(input_path, output_path):
    # 만약 output_path 디렉토리가 없다면 생성
    if not os.path.exists(output_path):
        os.makedirs(output_path)

    # input_path 디렉토리 안의 모든 WAV 파일에 대해 반복
    for filename in os.listdir(input_path):
        if filename.endswith(".wav"):
            # WAV 파일 로드
            file_path = os.path.join(input_path, filename)
            y, sr = librosa.load(file_path, sr=None)

            # 노이즈 제거 및 다른 전처리 작업 수행
            # 여기에 원하는 전처리 코드를 추가하세요
            D = librosa.amplitude_to_db(np.abs(librosa.stft(y)), ref=np.max)
            D_denoised = librosa.decompose.nn_filter(D, aggregate=np.median)
            y_denoised = librosa.griffinlim(librosa.db_to_amplitude(D_denoised))

            # 노이즈 제거
            y_denoised = librosa.effects.preemphasis(y)
            y_denoised, index = librosa.effects.trim(y_denoised)

            # 전처리된 오디오를 새로운 파일로 저장
            output_file_path = os.path.join(output_path, filename)
            sf.write(output_file_path, y_denoised, sr)
            print(f"Preprocessed {filename} and saved to {output_file_path}")

# 예제로 사용할 디렉토리 및 경로 설정
input_directory = "/content/extract"
output_directory = "/content/processed"

# 전처리 함수 호출
preprocess_audio(input_directory, output_directory)

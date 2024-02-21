def transcribe_and_save_to_csv(directory_path, csv_output_path, language_code='ko-KR'):
    client = speech.SpeechClient()
    transcriptions = []

    for filename in os.listdir(directory_path):
        file_path = os.path.join(directory_path, filename)

        if os.path.isfile(file_path) and filename.endswith(".wav"):
            converted_file_path = os.path.join(directory_path, f"converted_{filename}")
            convert_to_16bit_wav(file_path, converted_file_path)

            with open(converted_file_path, 'rb') as audio_file:
                content = audio_file.read()

            audio = speech.RecognitionAudio(content=content)
            config = speech.RecognitionConfig(
                encoding=speech.RecognitionConfig.AudioEncoding.LINEAR16,
                sample_rate_hertz=44100,
                language_code=language_code,
            )

            chunk_size = 1048576  # 1 MB
            chunks = [content[i:i+chunk_size] for i in range(0, len(content), chunk_size)]

            transcription_text = ''

            for chunk in chunks:
                audio_chunk = speech.RecognitionAudio(content=chunk)
                response = client.recognize(config=config, audio=audio_chunk)

                for result in response.results:
                    transcription_text += f"{result.alternatives[0].transcript}\n"

            os.remove(converted_file_path)

            # 레이블 추가
            transcriptions.append({'filename': filename, 'transcription': transcription_text, 'label': 1})

    with open(csv_output_path, 'w', newline='') as csv_file:
        fieldnames = ['filename', 'transcription', 'label']
        writer = csv.DictWriter(csv_file, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(transcriptions)

    files.download(csv_output_path)

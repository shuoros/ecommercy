<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecommercyApp.city.home.createOrEditLabel"
          data-cy="CityCreateUpdateHeading"
          v-text="$t('ecommercyApp.city.home.createOrEditLabel')"
        >
          Create or edit a City
        </h2>
        <div>
          <div class="form-group" v-if="city.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="city.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.city.name')" for="city-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="city-name"
              data-cy="name"
              :class="{ valid: !$v.city.name.$invalid, invalid: $v.city.name.$invalid }"
              v-model="$v.city.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('ecommercyApp.city.state')" for="city-state">State</label>
            <select class="form-control" id="city-state" data-cy="state" name="state" v-model="city.state">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="city.state && stateOption.id === city.state.id ? city.state : stateOption"
                v-for="stateOption in states"
                :key="stateOption.id"
              >
                {{ stateOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.city.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./city-update.component.ts"></script>

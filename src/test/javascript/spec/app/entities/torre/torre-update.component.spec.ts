/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import TorreUpdateComponent from '@/entities/torre/torre-update.vue';
import TorreClass from '@/entities/torre/torre-update.component';
import TorreService from '@/entities/torre/torre.service';

import ApartamentoService from '@/entities/apartamento/apartamento.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Torre Management Update Component', () => {
    let wrapper: Wrapper<TorreClass>;
    let comp: TorreClass;
    let torreServiceStub: SinonStubbedInstance<TorreService>;

    beforeEach(() => {
      torreServiceStub = sinon.createStubInstance<TorreService>(TorreService);

      wrapper = shallowMount<TorreClass>(TorreUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          torreService: () => torreServiceStub,

          apartamentoService: () => new ApartamentoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.torre = entity;
        torreServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(torreServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.torre = entity;
        torreServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(torreServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTorre = { id: 123 };
        torreServiceStub.find.resolves(foundTorre);
        torreServiceStub.retrieve.resolves([foundTorre]);

        // WHEN
        comp.beforeRouteEnter({ params: { torreId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.torre).toBe(foundTorre);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
